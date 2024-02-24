package tech.xavi.spacecraft.model;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ErrorPayload(
        String error,
        int code,
        String message,
        ZonedDateTime timestamp,
        String path
){
}
