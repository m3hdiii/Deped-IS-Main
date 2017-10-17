package com.deped.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private String targetUrl;

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        Object principal = authentication.getPrincipal();
//        if(principal != null) {
//            UserDetailsServiceImpl.CustomSpringSecurityUser userInfo = ((UserDetailsServiceImpl.CustomSpringSecurityUser) authentication.getPrincipal());
//            request.
//        }
//
        //String username = request.getParameter(filter.getUsernameParameter());
        response.sendRedirect(request.getContextPath() + targetUrl);

    }
}
