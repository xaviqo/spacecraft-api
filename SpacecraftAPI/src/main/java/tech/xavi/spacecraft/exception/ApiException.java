package tech.xavi.spacecraft.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter @AllArgsConstructor
public class ApiException extends RuntimeException{



    private final ApiError error;
    private final HttpStatus httpStatus;
}
