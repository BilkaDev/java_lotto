package pl.lotto.cache.redis;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.numbergenerator.INumberGeneratorFacade;
import pl.lotto.domain.numbergenerator.WinningNumbersNotFoundException;
import pl.lotto.domain.resultannouncer.IResultAnnouncerFacade;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.ResultDto;
import pl.lotto.infrastructure.numberreceiver.controller.InputNumbersResponseDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedisResultsCacheIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    @SpyBean
    IResultAnnouncerFacade resultAnnouncerFacade;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    public INumberGeneratorFacade numberGeneratorFacade;

    @Autowired
    public ResultCheckerFacade resultCheckerFacade;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    public void should_save_results_to_cache_and_then_invalidate_by_time_to_live() throws Exception {
        // step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        // given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [1,2,3,4,5,6,7,8,1,34,4,32,5,76,85,16,17,18,19,20,21,22,23,24,25]
                                """.trim())
                ));


        // step 2: system fetched winning numbers for draw date: 27.07.2024 12:00 Saturday
        // given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0);
        // when
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(
                        () -> {

                            try {
                                return !numberGeneratorFacade.retrieveWinningNumbersByDate(drawDate).winningNumbers().isEmpty();
                            } catch (WinningNumbersNotFoundException e) {
                                return false;
                            }
                        }
                );

        // step 3: user made POST /inputNumbers with 6 numbers (1,2,3,4,5,6) at 24-07-2024 10:00 and system returned ok(200)
        // with message: "success" and Ticket (DrawDate: 27.07.2024 12:00 Saturday, TicketId: sampleTicketId)

        // Given
        // When
        ResultActions postInputNumbers = mockMvc.perform(post("/api/v1/inputNumbers")
                .content("""
                        {
                            "numbers": [1,2,3,4,5,6]
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        MvcResult mvcResult = postInputNumbers.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        InputNumbersResponseDto inputNumbersResponseDto = objectMapper.readValue(json, InputNumbersResponseDto.class);
        String ticketId = inputNumbersResponseDto.ticket().ticketId();
        assertAll(
                () -> assertThat(inputNumbersResponseDto.ticket().numbers()).contains(1, 2, 3, 4, 5, 6),
                () -> assertThat(inputNumbersResponseDto.ticket().drawDate()).isEqualTo(drawDate),
                () -> assertThat(inputNumbersResponseDto.ticket().ticketId()).isNotEmpty(),
                () -> assertThat(inputNumbersResponseDto.message()).isEqualTo("SUCCESS")
        );


        // step 5: 3 days and 55 minutes passed, and it is 5 minute before draw date: 27.07.2024 11:55 Saturday
        // given
        clock.plusDaysAndMinutes(3, 55);


        // step 6: system generated result for TicketId: sampleTicketId with draw date 27.07.2024 12:00 Saturday,
        // and saved it with 6 hits
        await().atMost(20, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    try {
                        ResultDto byTicketId = resultCheckerFacade.findByTicketId(ticketId);
                        return !byTicketId.numbers().isEmpty();
                    } catch (Exception e) {
                        return false;
                    }
                });


        // step 7: 65 minutes passed, and it is 60 minute after the draw (27.07.2024 13:00 Saturday)
        // given
        clock.plusMinutes(66);


        // step 8: user made GET /results/sampleTicketId
        // and system save to cache
        // when
        mockMvc.perform(get("/api/v1/results/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
        );
        // then

        verify(resultAnnouncerFacade, times(1)).checkResult(ticketId);
        Collection<String> cacheNames = cacheManager.getCacheNames();
        assertThat(cacheNames.contains("results")).isTrue();


        // step 4: cache should be invalidated
        // given && when && then
        await()
                .atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/api/v1/results/" + ticketId)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                            );
                            verify(resultAnnouncerFacade, atLeast(2)).checkResult(ticketId);
                        }
                );
    }
}
