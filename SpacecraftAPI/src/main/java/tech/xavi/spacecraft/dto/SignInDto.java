package tech.xavi.spacecraft.dto;

import lombok.Builder;
import tech.xavi.spacecraft.entity.account.Role;

@Builder
public record SignInDto(
        long accountId,
        String username,
        Role role,
        String accessToken,
        String refreshToken
) {
}
