package tech.xavi.spacecraft.service.account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.xavi.spacecraft.entity.account.Account;
import tech.xavi.spacecraft.entity.account.RefreshToken;
import tech.xavi.spacecraft.repository.RefreshTokenRepository;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
@Service
public class JwtService {

    private final String ISSUER;
    private final Key SECRET;
    private final int ACCESS_TKN_EXP_SEC;
    private final int REFRESH_TKN_EXP_SEC;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final String TOKEN_PREFIX = "Bearer ";

    public JwtService(
            @Value("${xavi.tech.spacecraft.cfg.jwt.issuer}") String issuer,
            @Value("${xavi.tech.spacecraft.cfg.jwt.access-tkn-exp-sec}") int accessTknExpSec,
            @Value("${xavi.tech.spacecraft.cfg.jwt.refresh-tkn-exp-sec}") int refreshTknExpSec,
            RefreshTokenRepository repository
    ) {
        this.ISSUER = issuer;
        this.ACCESS_TKN_EXP_SEC = accessTknExpSec;
        this.REFRESH_TKN_EXP_SEC = refreshTknExpSec;
        this.SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.refreshTokenRepository = repository;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUsername(token)
                .equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token)
                .before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TKN_EXP_SEC))
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Account account){
        return refreshTokenRepository
                .save(RefreshToken.builder()
                        .token(generateToken(account))
                        .owner(account)
                        .revoked(false)
                        .issuedAt(LocalDateTime.now())
                        .build())
                .getToken();
    }

    public void invalidateRefreshToken(String refreshToken){
        refreshTokenRepository.findById(refreshToken)
                .ifPresent( storedToken -> {
                    storedToken.setRevoked(true);
                    refreshTokenRepository.save(storedToken);
                });

    }

    public Optional<String> getAccessTokenFromHeaders(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX))
            return Optional.of(authHeader.replace(TOKEN_PREFIX, ""));
        return Optional.empty();
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        return (Collection<? extends GrantedAuthority>)
                extractClaim(token, claims -> claims.get("roles", Collection.class))
                        .stream()
                        .map(str -> new SimpleGrantedAuthority((String) str))
                        .toList();
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        return claimResolver.apply(
                extractAllClaims(token)
        );
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
