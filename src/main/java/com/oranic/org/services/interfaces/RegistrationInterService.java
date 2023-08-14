package com.oranic.org.services.interfaces;

import com.oranic.org.playload.request.AccessTokenRequest;
import com.oranic.org.playload.request.RegTokenRequest;
import com.oranic.org.playload.response.AccessTokenResponse;
import com.oranic.org.playload.response.TokenValidationResponse;
public interface RegistrationInterService {
    TokenValidationResponse registerToken(RegTokenRequest param);
    AccessTokenResponse getTokenRegister(AccessTokenRequest request);
}
