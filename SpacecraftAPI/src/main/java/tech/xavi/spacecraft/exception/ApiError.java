package tech.xavi.spacecraft.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ApiError {

    INVALID_SPACECRAFT_ID(1000,"The spacecraft ID cannot be negative");

    private final int code;
    private final String message;
}
