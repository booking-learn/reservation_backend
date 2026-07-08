package ca.vetClinic.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class General {
    @GetMapping("/status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
