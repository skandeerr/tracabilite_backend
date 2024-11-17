package com.tracabilite.tracabilite.service.security;

import com.tracabilite.tracabilite.service.impl.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtUtils jwtUtils;

    private UserDetailsServiceImpl userDetailsService;


    public JwtConfigurer(JwtUtils jwtUtils ,  UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;

    }

   /* @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthTokenFilter customFilter = new AuthTokenFilter(jwtUtils , userDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }*/
}
