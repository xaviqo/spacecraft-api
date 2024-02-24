package tech.xavi.spacecraft.dto;

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
