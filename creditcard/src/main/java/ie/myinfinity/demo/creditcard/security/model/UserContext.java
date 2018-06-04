package ie.myinfinity.demo.creditcard.security.model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public final class UserContext  implements Serializable{
    private final String username;
    private final String userRole;
    private final List<GrantedAuthority> authorities;

    private UserContext(final String username, final String userRole, final List<GrantedAuthority> authorities) {
        super();
        this.username = username;
        this.userRole=userRole;
        this.authorities =new ArrayList<>(authorities);

    }
    
    public static UserContext create(String username, String userRole, List<GrantedAuthority> authorities) {
        if  (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        if  (StringUtils.isBlank(userRole)) throw new IllegalArgumentException("UserRo is blank: " + userRole);
        return new UserContext(username, userRole, authorities);
    }

    public String getUsername() {
        return username;
    }

    public String getUserRole() {
        return userRole;
    }

    public List<GrantedAuthority> getAuthorities() {

        return new ArrayList<>(authorities);
    }


}
