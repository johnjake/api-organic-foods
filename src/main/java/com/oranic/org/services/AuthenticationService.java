package com.oranic.org.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oranic.org.model.token.Token;
import com.oranic.org.model.token.TokenType;
import com.oranic.org.model.users.User;
import com.oranic.org.playload.request.AccessLogoutRequest;
import com.oranic.org.playload.request.AuthenticationRequest;
import com.oranic.org.playload.request.RegisterRequest;
import com.oranic.org.playload.response.AuthLogoutResponse;
import com.oranic.org.playload.response.AuthenticationResponse;
import com.oranic.org.repository.TokenRepository;
import com.oranic.org.repository.UserRepository;
import com.oranic.org.services.interfaces.AuthenticationInterService;
import com.oranic.org.services.interfaces.JwtInterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationInterService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtInterService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Integer userExistsByEmail(String email) {
        return repository.getTokenCountByEmail(email);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticateManager(request);
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthLogoutResponse logout(HttpServletRequest request) {
        final String tokenBearer;
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if ((authHeader != null) && authHeader.startsWith("Bearer ")) {
            tokenBearer = authHeader.substring(7);
            var tokenValid = tokenRepository.verifyValidityToken(tokenBearer);
            if (tokenValid > 0) return logoutResponse("Invalid token!");
            else {
                var tokenResult = tokenRepository.updateTokenToLogout(tokenBearer);
                if (tokenResult > 0)
                    return logoutResponse("Logout successful");
                else
                    return logoutResponse("An error occurred during logout!");
            }
        } else {
            return logoutResponse("Token not found in the request.");
        }
    }

    private AuthLogoutResponse logoutResponse(String param) {
        return AuthLogoutResponse
                .builder()
                .message(param)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void authenticateManager(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    }
}
