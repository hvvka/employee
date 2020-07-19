package com.github.hvvka.errorservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ErrorRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorRestController.class);

    @PostMapping("/exception")
    public ResponseEntity<String> postException(@RequestBody Exception exception) {
        LOG.info("POST Exception: {}", exception.getMessage());
        return ResponseEntity.ok().body("exception received");
    }
}
