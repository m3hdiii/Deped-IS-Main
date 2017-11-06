package com.deped.security;

import com.deped.model.account.User;
import com.deped.model.security.Privilege;
import com.deped.model.security.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResponseEntity<User> response = makeFetchByUsernameRequest("user", username, User.class);
        User user = response.getBody();
        return new CustomSpringSecurityUser(user, getAuthorities(user.getRoles()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (user.getUsername().equals("mehdi")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;

        //FIXME CONSIDER IT LATER
//        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Set<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


    public ResponseEntity<User> makeFetchByUsernameRequest(String baseName, String username, Class<User> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format("http://localhost:8074/rest/%s/fetch/%s", baseName, username);
        ResponseEntity<User> response = restTemplate.getForEntity(restUrl, entityClass);
        return response;
    }

    public class CustomSpringSecurityUser extends org.springframework.security.core.userdetails.User {
        private User user;

        public CustomSpringSecurityUser(User user, Collection<? extends GrantedAuthority> grantedAuthorities) {
            super(user.getUsername(), user.getPassword(),
                    true, true, true, true, grantedAuthorities);
            user.setPassword(null);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

}
