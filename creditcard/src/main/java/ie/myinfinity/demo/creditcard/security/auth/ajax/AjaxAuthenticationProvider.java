package ie.myinfinity.demo.creditcard.security.auth.ajax;

import ie.myinfinity.demo.creditcard.entities.User;
import ie.myinfinity.demo.creditcard.security.model.UserContext;
import ie.myinfinity.demo.creditcard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//import swat.cwa;

/**
 * @author vladimir.stankovic
 *         <p>
 *         Aug 3, 2016
 */
@Component("ajaxAuthenticationProvider")
public class AjaxAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    @Qualifier("UserService")
    private UserService userService;


    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();



        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
         if(!userService.matchUserAndPassword(username, password)){
             throw new AuthenticationServiceException("Authentication failed : password not matching" );
         }

        if (user.getRole() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole().getDescription().equals("ADMIN")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        UserContext userContext = UserContext.create(user.getUsername(), user.getRole().getDescription(), authorities);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
