package com.oranic.org.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtInterService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    public String generateRefreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
}
