package com.oranic.org.services.interfaces;

import com.oranic.org.playload.request.AuthenticationRequest;
import com.oranic.org.playload.request.RegisterRequest;
import com.oranic.org.playload.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationInterService {
    public AuthenticationResponse register(RegisterRequest request);
    public Integer userExistsByEmail(String email);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
