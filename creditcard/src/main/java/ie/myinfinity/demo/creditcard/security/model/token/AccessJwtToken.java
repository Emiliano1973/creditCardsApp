package ie.myinfinity.demo.creditcard.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

public final class AccessJwtToken implements JwtToken {
    private final String rawToken;
    @JsonIgnore
    private final Claims claims;

    AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
