package com.programandologicas.TaskManager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestErrorController {

    private static final Logger logger = LoggerFactory.getLogger(TestErrorController.class);

    @GetMapping("/api/test/error")
    public ResponseEntity<String> triggerError() {
        logger.info("Triggering test error endpoint");
        throw new RuntimeException("Test error triggered intentionally for logging verification");
    }
}
