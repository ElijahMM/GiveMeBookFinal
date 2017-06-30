package com.mihai.licenta.Configuration;

import com.mihai.licenta.Security.JWTAuthenticationFilter;
import com.mihai.licenta.Security.JWTLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by mihai on 29.03.2017.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/registerFacebook").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                .antMatchers("/api/resource/image/user/**").permitAll()
                .antMatchers("/api/resource/image/book/**").permitAll()
                .antMatchers("/api/categories/**").permitAll()
                .antMatchers("/api/recommendation/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JWTLoginFilter("/api/user/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("root")
                .roles("ADMIN");
    }


}
