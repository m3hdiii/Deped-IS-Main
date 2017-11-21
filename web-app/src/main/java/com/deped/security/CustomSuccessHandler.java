package com.deped.security;

import com.deped.controller.SharedData;
import com.deped.model.config.client.ClientEnumKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private String targetUrl;

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Map<ClientEnumKey, String> mapConfig = SharedData.getClientConfigsMap(false);
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("baseUrl", mapConfig.get(ClientEnumKey.RESOURCE_BASE_URL));

        response.sendRedirect(request.getContextPath() + targetUrl);


    }
}
