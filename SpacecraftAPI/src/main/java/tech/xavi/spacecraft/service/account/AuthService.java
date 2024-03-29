package tech.xavi.spacecraft.service.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import tech.xavi.spacecraft.dto.AccountDto;
import tech.xavi.spacecraft.dto.SignInDto;
import tech.xavi.spacecraft.entity.account.Account;
import tech.xavi.spacecraft.entity.account.Role;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.exception.ApiException;
import tech.xavi.spacecraft.repository.AccountRepository;

@Service @RequiredArgsConstructor
public class AuthService implements LogoutHandler {

    private final JwtService jwtService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder pwdEncoder;

    public SignInDto signIn(AccountDto signInRequest){
        return accountRepository
                .findAccountByUsernameIgnoreCase(signInRequest.username())
                .filter( account -> pwdEncoder.matches(signInRequest.password(), account.getPassword()) )
                .map(this::getSignInPayload)
                .orElseThrow( () -> new ApiException(ApiError.ACC_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public SignInDto createUserAccountAndSignIn(AccountDto accountDto){
        accountService.createAccount(accountDto, Role.USER);
        return signIn(accountDto);
    }

    public SignInDto renewAccountTokens(HttpServletRequest request) {
        return jwtService.getTokenFromHeaders(request)
                .filter(oldToken -> !jwtService.isRefreshTokenRevoked(oldToken))
                .flatMap(oldToken -> accountRepository.findAccountByUsernameIgnoreCase(jwtService.extractUsername(oldToken))
                        .filter(account -> jwtService.isTokenValid(oldToken, account) && !jwtService.isRefreshTokenRevoked(oldToken))
                        .map(account -> {
                            jwtService.invalidateRefreshToken(oldToken);
                            return getSignInPayload(account);
                        }))
                .orElseThrow(() -> new ApiException(ApiError.REF_TOKEN_NOT_VALID, HttpStatus.BAD_REQUEST));
    }

    private SignInDto getSignInPayload(Account account){
        return SignInDto.builder()
                .accountId(account.getId())
                .username(account.getUsername())
                .role(account.getRole())
                .accessToken(jwtService.generateToken(account,false))
                .refreshToken(jwtService.generateAndSaveRefreshToken(account))
                .build();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        jwtService.getTokenFromHeaders(request)
                .ifPresent(jwtService::invalidateRefreshToken);
        SecurityContextHolder.clearContext();
    }
}
