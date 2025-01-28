package org.senla.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StartController {

    @GetMapping
    public ResponseEntity<String> showIndexPage() {
        return ResponseEntity.ok("");
    }
}
