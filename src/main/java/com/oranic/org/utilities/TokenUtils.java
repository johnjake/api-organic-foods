package com.oranic.org.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TokenUtils {

    private static Claims extractAllClaims(String token, Key secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration,
            Key secretKey
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private static Date extractExpiration(String token, Key secretKey) {

        return extractClaim(token, Claims::getExpiration, secretKey);
    }
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver, Key secretKey) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    public static String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Key secretKey, Long jwtExpiration) {
        return buildToken(extraClaims, userDetails, jwtExpiration, secretKey);
    }

    public static String generateRefreshToken(UserDetails userDetails, Key secretKey, Long expiration) {
        return buildToken(new HashMap<>(), userDetails, expiration, secretKey);
    }

    public static Date tokenExpiration(String token, Key secretKey) {
        return extractExpiration(token, secretKey);
    }
}
