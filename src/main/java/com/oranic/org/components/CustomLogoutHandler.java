package com.oranic.org.components;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.logging.Logger;

public class CustomLogoutHandler implements LogoutHandler {
    public static final Logger LOG = Logger.getLogger(String.valueOf(CustomLogoutHandler.class));
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        var userName = authentication.getName();
        LOG.warning("username: "+ userName);
    }
}
