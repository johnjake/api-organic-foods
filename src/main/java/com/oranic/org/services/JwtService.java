package com.oranic.org.services;

import com.oranic.org.services.interfaces.JwtInterService;
import com.oranic.org.utilities.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService implements JwtInterService {

    @Override
    public String extractUsername(String token) {
        return TokenUtils.extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return TokenUtils.generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return TokenUtils.generateRefreshToken(userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return TokenUtils.tokenExpiration(token).before(new Date());
    }

}
