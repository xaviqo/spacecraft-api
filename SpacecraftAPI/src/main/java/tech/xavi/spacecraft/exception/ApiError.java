package tech.xavi.spacecraft.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ApiError {

    SC_ID_NOT_FOUND(1000,"There is no spacecraft with the provided id"),
    NEGATIVE_ID(1001,"The spacecraft ID cannot be negative"),
    ;

    private final int code;
    private final String message;
}
