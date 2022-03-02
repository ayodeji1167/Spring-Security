package com.example.security.security.filters;

import com.example.security.entity.Otp;
import com.example.security.repository.OtpRepo;
import com.example.security.security.authentications.UsernameOtpAuthentication;
import com.example.security.security.authentications.UsernamePasswordAuthentication;
import com.example.security.security.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;


@Component
public class UsernamePasswordAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private OtpRepo otpRepo;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Step 1 : Username and password
        //Step 2 : Username and otp

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if (otp == null) {

            //step 1

            UsernamePasswordAuthentication uapa = new UsernamePasswordAuthentication(username, password);
            Authentication a = manager.authenticate(uapa);

            //If it has been authenticated wit username and password, we have to generate otp for the user

            Otp otp1 = new Otp();
            otp1.setUsername(username);
            otp1.setOtp(otpGen());
            otpRepo.save(otp1);

        } else {

            UsernameOtpAuthentication uoa = new UsernameOtpAuthentication(username, otp);
            Authentication a = manager.authenticate(uoa);

            //We issue a token

            String token = UUID.randomUUID().toString();
            tokenManager.add(token);
            response.setHeader("Authorization", token);
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

    //Generating Otp
    private String otpGen() {

        return String.valueOf(new Random().nextInt(9999) + 1000);
    }
}
