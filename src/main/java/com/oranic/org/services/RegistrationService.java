package com.oranic.org.services;

import com.oranic.org.playload.request.AccessTokenRequest;
import com.oranic.org.playload.request.RegTokenRequest;
import com.oranic.org.playload.response.AccessTokenResponse;
import com.oranic.org.playload.response.TokenValidationResponse;
import com.oranic.org.repository.RegistrationTokenRepository;
import com.oranic.org.services.interfaces.RegistrationInterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements RegistrationInterService {
    @Autowired
    private RegistrationTokenRepository repository;
    @Override
    public TokenValidationResponse registerToken(RegTokenRequest param) {
        return null;
    }

    @Override
    public AccessTokenResponse getTokenRegister(AccessTokenRequest request) {
        return null;
    }
}
