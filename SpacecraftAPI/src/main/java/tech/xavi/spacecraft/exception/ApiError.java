package tech.xavi.spacecraft.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ApiError {

    SC_ID_NOT_FOUND(1000,"There is no spacecraft with the provided id"),
    NEGATIVE_ID(1001,"The spacecraft ID cannot be negative"),

    ACC_NAME_EXISTS(2000,"This username is already registered"),
    ACC_NOT_FOUND(2001,"Account not found");
    ;

    private final int code;
    private final String message;
}
