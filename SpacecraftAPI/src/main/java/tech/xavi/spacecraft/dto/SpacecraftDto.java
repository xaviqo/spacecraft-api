package tech.xavi.spacecraft.dto;

import lombok.Builder;
import tech.xavi.spacecraft.entity.spacecraft.Status;

@Builder
public record SpacecraftDto(
        long id,
        String name,
        long maxSpeed,
        int width,
        int height,
        int crewSize,
        Status status
) {
}