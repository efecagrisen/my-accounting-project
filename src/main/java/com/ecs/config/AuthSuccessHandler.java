package com.ecs.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("Root User")){
            response.sendRedirect("/companies/list");
        }

        if(roles.contains("Admin")){
            response.sendRedirect("/users/list");
        }

        if(roles.contains("Manager") || roles.contains("Employee")){
            response.sendRedirect("/dashboard");
        }

    }
}
