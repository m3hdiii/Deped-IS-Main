package com.deped.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomFailureHandler implements AuthenticationFailureHandler {

    private String targetUrl;

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }

        String username = request.getParameter("username");

        response.sendRedirect(request.getContextPath() + targetUrl + "?error&username=" + username);
    }
}
