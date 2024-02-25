package tech.xavi.spacecraft.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tech.xavi.spacecraft.exception.ApiError;
import tech.xavi.spacecraft.exception.ApiException;

@Log4j2
@Aspect
@Component
public class InterceptorAspect {

    @Before("execution(* tech.xavi.spacecraft.service.spacecraft.SpacecraftService.getSpacecraftById(..)) && args(id)")
    public void logBeforeGetSpacecraftById(long id) {
        if (id < 0) {
            log.error("Negative id requested in getSpacecraftById: {}", id);
            throw new ApiException(ApiError.NEGATIVE_ID, HttpStatus.BAD_REQUEST);
        }
    }


}
