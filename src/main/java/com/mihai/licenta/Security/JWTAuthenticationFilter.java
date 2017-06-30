package com.mihai.licenta.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by mihai on 30.03.2017.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = null;
        try {
            authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
        }catch (Exception ex){}

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
