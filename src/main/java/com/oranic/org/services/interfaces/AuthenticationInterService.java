package com.oranic.org.services.interfaces;

import com.oranic.org.playload.request.AccessLogoutRequest;
import com.oranic.org.playload.request.AuthenticationRequest;
import com.oranic.org.playload.request.RegisterRequest;
import com.oranic.org.playload.response.AuthLogoutResponse;
import com.oranic.org.playload.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationInterService {
    AuthenticationResponse register(RegisterRequest request);
    Integer userExistsByEmail(String email);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthLogoutResponse logout(HttpServletRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
