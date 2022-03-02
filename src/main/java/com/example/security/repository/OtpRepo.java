package com.example.security.repository;

import com.example.security.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepo extends JpaRepository<Otp, Integer> {

    Optional<Otp> findOtpByUsername(String username);
}
