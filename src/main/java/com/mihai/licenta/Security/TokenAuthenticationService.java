package com.mihai.licenta.Security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihai.licenta.Models.DBModels.User;
import com.mihai.licenta.Service.UserService;
import com.mihai.licenta.Utils.SpringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * Created by mihai on 30.03.2017.
 */
public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = 10000000; // ms
    static final String SECRET = "Licenta";
    static final String TOKEN_PREFIX = "Token";
    static final String HEADER_STRING = "Authorization";

    static UserService userService = SpringUtils.getBean(UserService.class);

    public static void addAuthentication(HttpServletResponse response, String email) {
        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.setContentType("application/json");

        User user = userService.findUserByEmail(email);
        if (user != null) {
            user.setToken(JWT);
            userService.saveUser(user,1);
            try {
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().write(mapper.writeValueAsString(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return userService.findUserByEmail(user).getToken() != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
        }
        return null;
    }

}
