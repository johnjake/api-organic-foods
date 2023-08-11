package com.oranic.org.components;

import com.oranic.org.utilities.LoggerUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    public static final Logger LOG = LoggerUtils.getLogger(CustomLogoutHandler.class);
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        var userName = authentication.getName();
        LOG.warn("username: "+ userName);
    }
}
