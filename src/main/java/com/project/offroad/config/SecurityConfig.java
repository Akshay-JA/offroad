package com.project.offroad.config;

import com.project.offroad.service.MyUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    public MyUserDetailsService userDetailsService;
    public SecurityFilterChain securityFilterChain (HttpSecurity http){
        http.csrf(custom -> custom.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }


    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider(userDetailsService);
        return provider;
    }
}
