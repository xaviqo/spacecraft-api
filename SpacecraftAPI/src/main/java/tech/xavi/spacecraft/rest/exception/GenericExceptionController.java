package tech.xavi.spacecraft.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.xavi.spacecraft.dto.ErrorDto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GenericExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex, HttpServletRequest request) {
        System.out.println("se llama el generico");
        HttpStatus status = getExceptionStatus(ex);
        ErrorDto errorDto = ErrorDto.builder()
                .error(status.name())
                .code(status.value())
                .message(ex.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(errorDto);
    }

    private HttpStatus getExceptionStatus(Exception exception){
        return switch (exception.getClass().getSimpleName()) {
            case "NoResourceFoundException" -> HttpStatus.NOT_FOUND;
            case "HttpMediaTypeNotSupportedException" -> HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            case "HttpRequestMethodNotSupportedException" -> HttpStatus.METHOD_NOT_ALLOWED;
            case "HttpMessageNotReadableException", "HttpMediaTypeException", "MethodArgumentTypeMismatchException" -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}


