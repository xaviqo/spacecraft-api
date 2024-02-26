package tech.xavi.spacecraft.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import tech.xavi.spacecraft.entity.account.Role;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
        return httpSec
                .csrf(AbstractHttpConfigurer::disable)
                .cors( cfg -> cfg.configurationSource( req -> getCorsConfiguration() ))
                .authorizeHttpRequests( req -> req
                        .requestMatchers(EndPoints.WHITE_LIST_EPS)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,EndPoints.EP_SPACECRAFT)
                        .hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,EndPoints.EP_SC_BY_ID)
                        .hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,EndPoints.EP_SC_BY_ID)
                        .hasAnyRole(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(smCfg -> smCfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout( logout -> logout
                        .logoutUrl(EndPoints.EP_ACC_LOGOUT)
                        .addLogoutHandler(logoutHandler))
                // DISABLE FRAME-OPTIONS FOR DB H2 CONSOLE
                // https://springframework.guru/using-the-h2-database-console-in-spring-boot-with-spring-security/
                .headers( headers -> headers.frameOptions().disable() )
                .build();
    }


    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Frame-Options"));
        return configuration;
    }

}
