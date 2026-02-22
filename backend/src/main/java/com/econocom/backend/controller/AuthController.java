package com.econocom.backend.controller;

import com.econocom.backend.dto.LoginRequest;
import com.econocom.backend.dto.LoginResponse;
import com.econocom.backend.dto.RefreshRequest;
import com.econocom.backend.dto.SsoResponse;
import com.econocom.backend.exception.CredencialesException;
import com.econocom.backend.service.AuthService;
import com.econocom.backend.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Angular
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SsoService ssoService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(
                    authService.login(request.getEmail(), request.getPassword())
            );
        } catch (CredencialesException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, e.getMessage()));
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        String newToken = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(Collections.singletonMap("accessToken", newToken));
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Token válido, acceso permitido!";
    }

    @GetMapping("/auth/sso")
    public ResponseEntity<Void> redirectToSsoProvider() {
        String ssoUrl = ssoService.buildSsoRedirectUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", ssoUrl);

        return ResponseEntity.status(302).headers(headers).build();
    }

    @GetMapping("/auth/sso/callback")
    public ResponseEntity<SsoResponse> ssoCallback(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String error) {
        SsoResponse response = ssoService.processCallback(code, error);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}
