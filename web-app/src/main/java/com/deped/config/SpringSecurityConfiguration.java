package com.deped.config;

import com.deped.security.CustomFailureHandler;
import com.deped.security.CustomSuccessHandler;
import com.deped.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.userDetailsService(userDetailsServiceProvider());

    }

    @Bean
    protected UserDetailsService userDetailsServiceProvider() {
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/update/{userId}").access("@centralizedAccessControl.checkUserChangeInfoAccess(authentication, #userId) or hasRole('ROLE_ADMIN')")
                .antMatchers("/**").permitAll();


//                .antMatchers("/login").permitAll()
//
//                .antMatchers("/brand/**").access("hasRole('ROLE_ADMIN')")
//                .antMatchers("user/**").access("hasRole('ROLE_ADMIN')");


        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/performLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());


        http.logout()
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .logoutUrl("/logout");


        http.csrf().disable();
//
//        http.sessionManagement().
//                enableSessionUrlRewriting(false);

//        http.portMapper().http(8493).mapsTo(8443);
//        http.portMapper().http(80).mapsTo(443);

//        http.headers()
//                .xssProtection()
//                .frameOptions()
//                .httpStrictTransportSecurity()
//                .cacheControl();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomSuccessHandler successHandler = new CustomSuccessHandler();
        successHandler.setTargetUrl("dashboard");
        return successHandler;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        CustomFailureHandler failureHandler = new CustomFailureHandler();
        failureHandler.setTargetUrl("login");
        return failureHandler;
    }
}
