package pl.lotto.infrastructure.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.Code;
import pl.lotto.domain.common.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Log4j2
public class GlobalExceptionRestHandlerConfiguration {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorDto> handleBaseException(BaseException e) {
        log.error("Base exception occurred: {}", e.getMessage());
        String message = e.getMessage();
        String code = e.getCode();
        HttpStatus statusCode = e.getStatusCode();
        ApiErrorDto response = ApiErrorDto.builder()
                .code(code)
                .messages(List.of(message))
                .status(statusCode)
                .build();
        return ResponseEntity.status(statusCode.getValue()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials exception occurred: {}", ex.getMessage());
        ApiErrorDto response = ApiErrorDto.builder()
                .code(Code.A1.toString())
                .messages(List.of(Code.A1.getLabel()))
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.getValue()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("Validation error occurred: {}", ex.getMessage());
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getField() + " " + error.getDefaultMessage()));
        ApiErrorDto response = ApiErrorDto.builder()
                .code("VALIDATION_ERROR")
                .messages(errors)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.getValue()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiErrorDto> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiErrorDto.ApiErrorDtoBuilder messages = ApiErrorDto.builder()
                .code("INTERNAL_SERVER_ERROR")
                .messages(getErrorsInternalServer(errors));
        ApiErrorDto response = messages
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.getValue()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ApiErrorDto> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        List<String> messages = getErrorsInternalServer(errors);
        ApiErrorDto response = ApiErrorDto.builder()
                .code("INTERNAL_SERVER_ERROR")
                .messages(messages)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.getValue()).body(response);
    }


    private List<String> getErrorsInternalServer(List<String> errors) {
        log.error(errors.toString());
        List<String> errorsMessage = new ArrayList<>();
        errorsMessage.add("Something went wrong, please try later");
        return errorsMessage;
    }
}
