package com.oranic.org.services;

import com.oranic.org.services.interfaces.JwtInterService;
import com.oranic.org.utilities.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService implements JwtInterService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Override
    public String extractUsername(String token) {
        var keys = getSignInKey(secretKey);
        return TokenUtils.extractClaim(token, Claims::getSubject, keys);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        var keys = getSignInKey(secretKey);
        var expiration = jwtExpiration;
        return TokenUtils.generateToken(new HashMap<>(), userDetails, keys, expiration);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        var keys = getSignInKey(secretKey);
        var expiration = refreshExpiration;
        return TokenUtils.generateRefreshToken(userDetails, keys, expiration);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        var keys = getSignInKey(secretKey);
        return TokenUtils.tokenExpiration(token, keys).before(new Date());
    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
