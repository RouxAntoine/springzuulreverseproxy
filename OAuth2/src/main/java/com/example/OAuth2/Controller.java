package com.example.OAuth2;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Controller {

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("{\"name\": \"test\"}");
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @GetMapping(value = "/foo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> foo() {

        String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok("{\"name\": " + userDetails + "}");
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal getInfo(Principal principal) {

        return principal;
    }
}
