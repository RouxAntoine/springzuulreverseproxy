package com.example.OAuth2;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("{\"name\": \"test\"}");
    }

    @GetMapping(value = "/foo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> foo() {

        return ResponseEntity.ok("{\"name\": \"foo\"}");
    }
}
