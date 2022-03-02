package com.example.security.security.providers;

import com.example.security.entity.Otp;
import com.example.security.repository.OtpRepo;
import com.example.security.security.authentications.UsernameOtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class OtpAuthProvider implements AuthenticationProvider {
    @Autowired
    private OtpRepo otpRepo;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        Optional<Otp> otp1 = otpRepo.findOtpByUsername(username);
        if (otp1.isPresent()) {

            return new UsernameOtpAuthentication(username, otp, List.of(() -> "read"));
        }

        throw new BadCredentialsException("Invalid Otp");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameOtpAuthentication.class.equals(authentication);
    }
}
