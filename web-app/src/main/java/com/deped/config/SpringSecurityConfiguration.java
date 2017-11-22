package com.deped.config;

import com.deped.security.CustomFailureHandler;
import com.deped.security.CustomSuccessHandler;
import com.deped.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowire;
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
@EnableWebSecurity(debug = true)
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
        http
                .authorizeRequests()
                //.antMatchers("/user/update/{userId}").access("@centralizedAccessControl.checkUserChangeInfoAccess(authentication, #userId) or hasRole('ROLE_ADMIN')")


                .antMatchers("/order/create").hasRole("SUPPLY_OFFICER")
                .antMatchers("/order-details/create/*").hasRole("SUPPLY_OFFICER")
                .antMatchers("/order/approval-list").hasRole("CHIEF")
                .antMatchers("/order/requisition-list").hasRole("SUPPLY_OFFICER")
                .antMatchers("/order/arrival-list").hasRole("SUPPLY_OFFICER")
                .antMatchers("/refresh-all").hasRole("ADMIN")

                .antMatchers("/category/list").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')")
                .antMatchers("/pack/list").access("hasRole('ROLE_ADMIN')  OR hasRole('ROLE_SUPPLY_OFFICER')")
                .antMatchers("/brand/list").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')")
                .antMatchers("/order/list").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/request/list").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/item/list").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')")

                .antMatchers("/supplier/list").access("hasRole('ROLE_ADMIN')  OR hasRole('ROLE_SUPPLY_OFFICER')")
                .antMatchers("/supplier/create").hasRole("SUPPLY_OFFICER")
                .antMatchers("/supplier/update/*").hasRole("SUPPLY_OFFICER")
                .antMatchers("/supplier/*").denyAll()

                .antMatchers("/item/**").hasRole("SUPPLY_OFFICER")
                .antMatchers("/category/**").hasRole("SUPPLY_OFFICER")
                .antMatchers("/brand/**").hasRole("SUPPLY_OFFICER")
                .antMatchers("/pack/**").hasRole("SUPPLY_OFFICER")
                .antMatchers("/department/**").hasRole("ADMIN")
                .antMatchers("/section/**").hasRole("ADMIN")

                .antMatchers("/role/**").hasRole("ADMIN")

                .antMatchers("/user/update/*", "/user/create").hasRole("ADMIN")
                .antMatchers("/user/list").access("hasRole('ROLE_CHIEF') OR hasRole('ROLE_ADMIN')")
                .antMatchers("/user/{userId}").access("@centralizedAccessControl.checkUserChangeInfoAccess(authentication, #userId) OR hasRole('ROLE_CHIEF') OR hasRole('ROLE_ADMIN')")

                .antMatchers("/request/create").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_PERSONNEL') OR hasRole('ROLE_CHIEF') OR hasRole('ROLE_SUPPLY_OFFICER')")
                .antMatchers("/request-details/create/*").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_PERSONNEL') OR hasRole('ROLE_CHIEF') OR hasRole('ROLE_SUPPLY_OFFICER')")

                .antMatchers("/request/approval-list").access("hasRole('ROLE_CHIEF') OR hasRole('ROLE_SUPPLY_OFFICER')")
                .antMatchers("/request-details/approval/*").access("hasRole('ROLE_CHIEF') OR hasRole('ROLE_SUPPLY_OFFICER')")

                .antMatchers("/request/release-list").hasRole("SUPPLY_OFFICER")
                .antMatchers("/request-details/issue/*").hasRole("SUPPLY_OFFICER")

                .antMatchers("/dashboard").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_PERSONNEL') OR hasRole('ROLE_CHIEF') OR hasRole('ROLE_SUPPLY_OFFICER')")

                .antMatchers("/", "/login/", "/forgot-password").permitAll();

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

        http.sessionManagement().
                enableSessionUrlRewriting(false);

//        http.portMapper().http(8493).mapsTo(8443);
//        http.portMapper().http(80).mapsTo(443);

        //ENABLED BY DEFAULT

        http.headers()
                .xssProtection().and()
                .frameOptions().and()
                .httpStrictTransportSecurity().and()
                .cacheControl().and().referrerPolicy();

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomSuccessHandler successHandler = new CustomSuccessHandler();
        successHandler.setTargetUrl("dashboard");
        return successHandler;
    }

    @Bean(autowire = Autowire.BY_TYPE)
    public AuthenticationFailureHandler authenticationFailureHandler() {
        CustomFailureHandler failureHandler = new CustomFailureHandler();
        failureHandler.setTargetUrl("login");
        return failureHandler;
    }
}
