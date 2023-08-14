package com.oranic.org.controller;

import com.oranic.org.playload.request.AccessLogoutRequest;
import com.oranic.org.playload.request.AuthenticationRequest;
import com.oranic.org.playload.request.RegisterRequest;
import com.oranic.org.playload.response.AuthLogoutResponse;
import com.oranic.org.playload.response.AuthenticationResponse;
import com.oranic.org.services.interfaces.AuthenticationInterService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationInterService service;
    private final AuthenticationResponse authResponse = new AuthenticationResponse();
    private final AuthLogoutResponse logoutResponse = new AuthLogoutResponse();

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        var result = service.authenticate(request);
        var tokenValue = result.getAccessToken();
        var cookie = new Cookie("accessToken", tokenValue);
        System.out.println("******Cookie Token ****** " + tokenValue);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        response.addCookie(cookie);
        authResponse.setAccessToken(tokenValue);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthLogoutResponse> logout(
            HttpServletRequest request
    ) {
        var response = service.logout(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
