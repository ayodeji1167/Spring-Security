package com.example.security.security.providers;

import com.example.security.security.authentications.TokenAuthentication;
import com.example.security.security.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenProvider implements AuthenticationProvider {
    @Autowired
    private TokenManager tokenManager;


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String token = authentication.getName();

        boolean exist = tokenManager.contains(token);
        if (exist){
            return new TokenAuthentication(token, null, List.of());
        }
        else
            throw new BadCredentialsException("Invalid token");

    }

    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
