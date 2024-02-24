package tech.xavi.spacecraft.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.xavi.spacecraft.dto.ErrorDto;
import tech.xavi.spacecraft.exception.ApiException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SpecificExceptionController {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(
            ApiException apiException, HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error(apiException.getError().name())
                        .code(apiException.getError().getCode())
                        .message(apiException.getError().getMessage())
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .path(request.getRequestURI())
                        .build(),
                apiException.getHttpStatus()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(
            ConstraintViolationException cvException, HttpServletRequest request
    ) {
        String message = cvException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Value validation error");

        return new ResponseEntity<>(
                ErrorDto.builder()
                        .error("VALIDATION")
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}