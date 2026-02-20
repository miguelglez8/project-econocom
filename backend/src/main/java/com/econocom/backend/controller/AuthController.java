package com.econocom.backend.controller;

import com.econocom.backend.dto.LoginRequest;
import com.econocom.backend.dto.LoginResponse;
import com.econocom.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Angular
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                authService.login(request.getEmail(), request.getPassword())
        );
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String newToken = authService.refreshToken(request.get("refreshToken"));
        return ResponseEntity.ok(Collections.singletonMap("accessToken", newToken));
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Token válido, acceso permitido!";
    }
}
