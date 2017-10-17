package com.deped.security;

import com.deped.model.account.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;


@Controller(value = "centralizedAccessControl")
public class CentralizedAccessControl {

    public boolean checkUserChangeInfoAccess(Authentication authentication, Long id) {
        User user = ((UserDetailsServiceImpl.CustomSpringSecurityUser) authentication.getPrincipal()).getUser();
        Long userId = user.getUserId();
        return userId == id;

    }
}
