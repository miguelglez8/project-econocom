package com.econocom.backend.service;

import com.econocom.backend.dto.LoginResponse;
import com.econocom.backend.exception.CredencialesException;
import com.econocom.backend.exception.RefreshTokenException;
import com.econocom.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(String email, String password) {
        // Simulación sin base de datos
        if (!email.equals("admin@test.com") || !password.equals("123a")) {
            throw new CredencialesException("Credenciales inválidas");
        }

        String accessToken = jwtUtil.generateToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        return new LoginResponse(accessToken, refreshToken);
    }

    public String refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RefreshTokenException("Refresh token inválido");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        return jwtUtil.generateToken(username);
    }
}
