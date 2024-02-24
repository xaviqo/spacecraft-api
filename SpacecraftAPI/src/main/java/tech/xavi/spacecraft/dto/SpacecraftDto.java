package tech.xavi.spacecraft.dto;

import tech.xavi.spacecraft.entity.Status;

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