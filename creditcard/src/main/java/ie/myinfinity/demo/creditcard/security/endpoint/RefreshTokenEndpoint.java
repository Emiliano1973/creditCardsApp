package ie.myinfinity.demo.creditcard.security.endpoint;

import ie.myinfinity.demo.creditcard.security.auth.jwt.extractor.TokenExtractor;
import ie.myinfinity.demo.creditcard.security.auth.jwt.verifier.TokenVerifier;
import ie.myinfinity.demo.creditcard.security.config.JwtSettings;
import ie.myinfinity.demo.creditcard.security.config.WebSecurityConfig;
import ie.myinfinity.demo.creditcard.security.exceptions.InvalidJwtToken;
import ie.myinfinity.demo.creditcard.security.model.UserContext;
import ie.myinfinity.demo.creditcard.security.model.token.JwtToken;
import ie.myinfinity.demo.creditcard.security.model.token.JwtTokenFactory;
import ie.myinfinity.demo.creditcard.security.model.token.RawAccessJwtToken;
import ie.myinfinity.demo.creditcard.security.model.token.RefreshToken;
import ie.myinfinity.demo.creditcard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RefreshTokenEndpoint {
    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired
    private JwtSettings jwtSettings;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenVerifier tokenVerifier;
    @Autowired
    @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/token", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        System.out.println("Token: " + tokenPayload);

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken
                refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();

        ie.myinfinity.demo.creditcard.entities.User user = userService.findByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (user.getRole() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(user.getRole().getDescription().equals("ADMIN")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        UserContext userContext = UserContext.create(user.getUsername(), user.getRole().getDescription(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
