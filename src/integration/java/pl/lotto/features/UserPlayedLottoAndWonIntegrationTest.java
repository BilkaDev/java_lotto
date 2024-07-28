package pl.lotto.features;

import org.junit.jupiter.api.Test;
import pl.lotto.BaseIntegrationTest;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {
    @Test
    public void should_user_win_and_system_should_generate_winners() {

        // step 1: user made POST /inputNumbers with 6 numbers (1,2,3,4,5,6) at 24-07-2024 10:00 and system returned ok(200)
        // with message: "success" and Ticket (DrawDate: 27.07.2024 12:00 Saturday, TicketId: sampleTicketId)

        // Given

        // When

        // Then

        // step 2: system generated winning numbers for draw date: 27.07.2024 12:00 Saturday
        // step 3: 3 days and 1 minute passed, and it is 1 minute after draw date: 30.07.2024 12:01 Saturday
        // step 4: system generated result for TicketId: sampleTicketId with draw date 27.07.2024 12:00 Saturday
        // step 5: 3 hours passed, and it is 1 minute after announcement time (27.07.2024 15:01 Saturday)
        // step 6: user made GET /results/sampleTicketId and system returned ok(200)


    }
}

