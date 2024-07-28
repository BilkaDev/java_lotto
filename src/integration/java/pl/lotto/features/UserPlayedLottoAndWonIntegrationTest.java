package pl.lotto.features;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lotto.BaseIntegrationTest;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {
    @Test
    public void should_user_win_and_system_should_generate_winners() {
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

        // when

        // then


        // step 2: system fetched winning numbers for draw date: 27.07.2024 12:00 Saturday
        // step 3: user made POST /inputNumbers with 6 numbers (1,2,3,4,5,6) at 24-07-2024 10:00 and system returned ok(200)
        // with message: "success" and Ticket (DrawDate: 27.07.2024 12:00 Saturday, TicketId: sampleTicketId)

        // Given

        // When

        // Then

        // step 4: 3 days and 1 minute passed, and it is 1 minute after draw date: 30.07.2024 12:01 Saturday
        // step 5: system generated result for TicketId: sampleTicketId with draw date 27.07.2024 12:00 Saturday
        // step 6: 3 hours passed, and it is 1 minute after announcement time (27.07.2024 15:01 Saturday)
        // step 7: user made GET /results/sampleTicketId and system returned ok(200)


    }
}

