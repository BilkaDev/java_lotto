package pl.lotto.apivalidationerror;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.infrastructure.config.ApiErrorDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_and_message_when_request_have_empty_body() throws Exception {
        // given
        String mockRequestBody = """
                {}
                """;

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/inputNumbers")
                .content(mockRequestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // then

        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiErrorDto apiErrorDto = objectMapper.readValue(json, ApiErrorDto.class);

        assertAll(
                () -> assertThat(apiErrorDto.messages()).asList().containsExactlyInAnyOrder(
                        "numbers must not be null",
                        "numbers must not be empty"
                ),
                () -> assertThat(apiErrorDto.code()).isEqualTo("VALIDATION_ERROR"),
                () -> assertThat(apiErrorDto.status().toString()).isEqualTo("BAD_REQUEST")
        );

    }

    @Test
    public void should_return_400_bad_request_and_message_when_request_does_not_have_input_numbers() throws Exception {
        // given
        String mockRequestBody = """
                {
                "numbers": []
                }
                """;

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/inputNumbers")
                .content(mockRequestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiErrorDto apiErrorDto = objectMapper.readValue(json, ApiErrorDto.class);

        assertAll(
                () -> assertThat(apiErrorDto.messages()).asList().containsExactlyInAnyOrder(
                        "numbers must not be empty"
                ),
                () -> assertThat(apiErrorDto.code()).isEqualTo("VALIDATION_ERROR"),
                () -> assertThat(apiErrorDto.status().toString()).isEqualTo("BAD_REQUEST")
        );
    }
}
