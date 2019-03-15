//package com.example.OAuth2;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//@Component
//public class CustomAuthenticationManager implements AuthenticationManager {
//
//    private final DaoAuthenticationProvider authenticationProvider;
//
//    public CustomAuthenticationManager() {
//
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(new User("john", "123", Collections.singleton(new SimpleGrantedAuthority("USER"))));
//
//
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsManager);
//
//        authenticationProvider = daoAuthenticationProvider;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return authenticationProvider.authenticate(authentication);
//    }
//}
