package com.oranic.org.utilities;

public class HrefUtils {
    public static final String[] whiteListedRoutes = new String[]{
            "/",
            "/index",
            "/css/**",
            "/js/**",
            "/fonts/**",
            "/img/**",
            "/sass/**",
            "/source/**",
            "/vue-app/**",
            "/sign-in",
            "/sign-up",
            "/favicon.ico",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/user/reg_token/{email}",
            "/api/user/reg_token/"
    };

    public static final String[] templatesRoutes = new String[] {
            "/shop-grid/**",
            "/shop-details/**",
            "/shoping-cart/**",
            "/products/**",
            "/checkout/**"
    };
}
