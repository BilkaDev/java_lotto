package pl.lotto.features;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.numbergenerator.NumberGeneratorFacade;
import pl.lotto.domain.numbergenerator.WinningNumbersNotFoundException;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.ResultDto;
import pl.lotto.infrastructure.numberreceiver.controller.InputNumbersResponseDto;
import pl.lotto.infrastructure.resultannoucer.controller.CheckResultResponseDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {
    @Autowired
    public NumberGeneratorFacade numberGeneratorFacade;

    @Autowired
    public ResultCheckerFacade resultCheckerFacade;

    @Test
    public void should_user_win_and_system_should_generate_winners() throws Exception {
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


        // step 4: user made GET /results/notExistingTicketId and system returned not found(404) and with
        // (message: "Not found for id: notExistingId", and status: "NOT_FOUND")
        // when
        ResultActions getResultNotFoundId = mockMvc.perform(get("/api/v1/results/notExistingId")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        getResultNotFoundId
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .json("""
                                {
                                    "messages":["Not found for id: notExistingId"],
                                    "status":"NOT_FOUND",
                                    "code":"PLAYER_RESULT_NOT_FOUND"
                                }
                                """.trim())
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


        // step 8: user made GET /results/sampleTicketId and system returned ok(200)
        // when
        ResultActions successResultPerform = mockMvc.perform(get("/api/v1/results/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        MvcResult mvcResultGetResults = successResultPerform.andExpect(status().isOk()).andReturn();
        String jsonGetResults = mvcResultGetResults.getResponse().getContentAsString();
        CheckResultResponseDto responseDto = objectMapper.readValue(jsonGetResults, CheckResultResponseDto.class);
        assertAll(
                () -> assertThat(responseDto.result().numbers()).contains(1, 2, 3, 4, 5, 6),
                () -> assertThat(responseDto.result().drawDate()).isEqualTo(drawDate),
                () -> assertThat(responseDto.result().hash()).isEqualTo(ticketId),
                () -> assertThat(responseDto.result().hitNumbers()).hasSize(6),
                () -> assertThat(responseDto.result().isWinner()).isEqualTo(true),
                () -> assertThat(responseDto.message()).isEqualTo("Congratulations, you won!")
        );
    }
}

