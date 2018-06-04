package ie.myinfinity.demo.creditcard.security.model.token;

import ie.myinfinity.demo.creditcard.security.config.JwtSettings;
import ie.myinfinity.demo.creditcard.security.model.Scopes;
import ie.myinfinity.demo.creditcard.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     * 
     * @param userContext
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (StringUtils.isBlank(userContext.getUserRole()))
            throw new IllegalArgumentException("Cannot create JWT Token without userRole");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");




        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("userRole", userContext.getUserRole());
        claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        ZonedDateTime currentTime = ZonedDateTime.now();

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(Date.from(currentTime.toInstant()))
          .setExpiration(Date.from(currentTime.plusMinutes(settings.getTokenExpirationTime()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        ZonedDateTime currentTime = ZonedDateTime.now();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("userRole", userContext.getUserRole());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(Date.from(currentTime.toInstant()))
          .setExpiration(Date.from(currentTime.plusMinutes(settings.getRefreshTokenExpTime()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
}
