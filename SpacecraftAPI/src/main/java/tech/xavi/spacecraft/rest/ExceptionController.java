package tech.xavi.spacecraft.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.xavi.spacecraft.exception.ApiException;
import tech.xavi.spacecraft.dto.ErrorPayload;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorPayload> springFoodExceptionHandler(
            ApiException apiException, HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                ErrorPayload.builder()
                        .error(apiException.getError().name())
                        .code(apiException.getError().getCode())
                        .message(apiException.getError().getMessage())
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .path(request.getRequestURI())
                        .build(),
                apiException.getHttpStatus()
        );
    }


}
