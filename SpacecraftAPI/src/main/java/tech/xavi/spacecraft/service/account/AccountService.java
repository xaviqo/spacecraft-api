package tech.xavi.spacecraft.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.xavi.spacecraft.dto.AccountDto;
import tech.xavi.spacecraft.entity.account.Account;
import tech.xavi.spacecraft.entity.account.Role;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.exception.ApiException;
import tech.xavi.spacecraft.repository.AccountRepository;

@Service @RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder pwdEncoder;

    public void createAccount(AccountDto newAccount, Role role){
        boolean isUsernameTaken = accountRepository
                .existsAccountByUsernameIgnoreCase(newAccount.username());
        if (isUsernameTaken)
            throw new ApiException(
                    ApiError.ACC_NAME_EXISTS,
                    HttpStatus.BAD_REQUEST
            );
        accountRepository.save(
                Account.builder()
                        .username(newAccount.username())
                        .password(pwdEncoder.encode(newAccount.password()))
                        .role(role)
                        .build()
        );
    }
}
