package ie.myinfinity.demo.creditcard.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.myinfinity.demo.creditcard.security.RestAuthenticationEntryPoint;
import ie.myinfinity.demo.creditcard.security.auth.ajax.AjaxLoginProcessingFilter;
import ie.myinfinity.demo.creditcard.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import ie.myinfinity.demo.creditcard.security.auth.jwt.SkipPathRequestMatcher;
import ie.myinfinity.demo.creditcard.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    private static final String USER_ADD_FORM_POINT = "/api/ext/users";
    private static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
    private static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    @Qualifier("ajaxAuthenticationProvider")
    private AuthenticationProvider ajaxAuthenticationProvider;


    @Autowired
    @Qualifier("jwtAuthenticationProvider")
    private AuthenticationProvider jwtAuthenticationProvider;
    
    @Autowired
    private TokenExtractor tokenExtractor;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private ObjectMapper objectMapper;
        
    private AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
    
    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT, USER_ADD_FORM_POINT);
        RequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter 
            = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable() // We don't need CSRF for JWT based authentication
        .exceptionHandling()
        .authenticationEntryPoint(this.authenticationEntryPoint)
        
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
            .authorizeRequests()
                .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
                .antMatchers(USER_ADD_FORM_POINT).permitAll()
        .and()
            .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
        .and()
            .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
