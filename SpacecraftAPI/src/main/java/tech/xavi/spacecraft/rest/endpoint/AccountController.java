package tech.xavi.spacecraft.rest.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.spacecraft.configuration.EndPoints;
import tech.xavi.spacecraft.dto.AccountDto;
import tech.xavi.spacecraft.dto.SignInDto;
import tech.xavi.spacecraft.service.account.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AccountController {

    private final AuthService authService;

    @PostMapping(EndPoints.EP_AC_SIGN_UP)
    public ResponseEntity<SignInDto> signUp(@RequestBody AccountDto dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.createUserAccountAndSignIn(dto));
    }

    @PostMapping(EndPoints.EP_AC_SIGN_IN)
    public ResponseEntity<SignInDto> signIn(@RequestBody AccountDto dto){
        return ResponseEntity.ok().body(authService.signIn(dto));
    }

}
