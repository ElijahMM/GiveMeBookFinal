package com.mihai.licenta.Security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihai.licenta.Models.InternModels.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by mihai on 30.03.2017.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {


    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginUser creds = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
        System.out.println(creds.getEmail() + " " + creds.getPassword());
        System.out.println(request);
        UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(
                creds.getEmail(),
                creds.getPassword(),
                Collections.emptyList()
        );
        CustomAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager();
        return customAuthenticationManager.authenticate(a);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(response, authResult.getName());
    }
}
