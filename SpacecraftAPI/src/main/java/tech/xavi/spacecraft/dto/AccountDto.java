package tech.xavi.spacecraft.dto;

import lombok.Builder;

@Builder
public record AccountDto(
        String username,
        String password
) {
}
