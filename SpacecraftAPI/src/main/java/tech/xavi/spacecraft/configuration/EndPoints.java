package tech.xavi.spacecraft.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class EndPoints {

    // API ROOT
    public static final String EP_VERSION = "/v1";
    public static final String EP_API_ROOT = EP_VERSION + "/api";

    // SPACECRAFT
    public static final String EP_SPACECRAFT = EP_API_ROOT + "/spacecraft";
    public static final String EP_SC_BY_ID = EP_SPACECRAFT + "/{id}";
    public static final String EP_SC_NAME_CONTAINS = EP_SPACECRAFT + "/search";

    // ACCOUNT
    public static final String EP_ACCOUNT = EP_API_ROOT + "/account";
    public static final String EP_AC_SIGN_UP = EP_ACCOUNT + "/sign-up";
    public static final String EP_AC_SIGN_IN = EP_ACCOUNT + "/sign-in";
    public static final String EP_ACC_LOGOUT = EP_ACCOUNT + "/logout";
    public static final String EP_ACC_REFRESH_TOKEN = EP_ACCOUNT + "/refresh";

    // WHITE LISTED ENDPOINTS
    public static final RequestMatcher[] WHITE_LIST_EPS = {
            new AntPathRequestMatcher("/h2-console/**",HttpMethod.POST.name()),
            new AntPathRequestMatcher("/h2-console/**",HttpMethod.GET.name()),
            new AntPathRequestMatcher("/swagger-ui/**",HttpMethod.GET.name()),
            new AntPathRequestMatcher("/openapi/**",HttpMethod.GET.name()),
            new AntPathRequestMatcher(EP_SPACECRAFT, HttpMethod.GET.name()),
            new AntPathRequestMatcher(EP_SC_BY_ID, HttpMethod.GET.name()),
            new AntPathRequestMatcher(EP_AC_SIGN_UP, HttpMethod.POST.name()),
            new AntPathRequestMatcher(EP_AC_SIGN_IN, HttpMethod.POST.name()),
            new AntPathRequestMatcher(EP_ACC_LOGOUT, HttpMethod.POST.name()),
            new AntPathRequestMatcher(EP_ACC_REFRESH_TOKEN, HttpMethod.POST.name()),
    };

}
