package com.econocom.backend.service;

import com.econocom.backend.dto.SsoResponse;
import org.springframework.stereotype.Service;

@Service
public class SsoService {

    private static final String SIMULATED_CODE = "SIMULATED_CODE_123";

    public String buildSsoRedirectUrl() {
        String redirectUri = "http://localhost:4200/sso/callback";

        return redirectUri + "?code=" + SIMULATED_CODE;
    }

    public SsoResponse processCallback(String code, String error) {
        // 🔴 Caso 1: el proveedor devuelve un error
        if (error != null) {
            return new SsoResponse(false,
                    "Autenticación SSO fallida: " + error,
                    null);
        }

        // 🔴 Caso 2: no se recibió código
        if (code == null || code.isEmpty()) {
            return new SsoResponse(false,
                    "No se recibió código de autorización",
                    null);
        }

        // 🟢 Caso 3: código válido (simulado)
        if (SIMULATED_CODE.equals(code)) {
            // Aquí luego puedes generar JWT real
            String simulatedToken = "SIMULATED_JWT_TOKEN";

            return new SsoResponse(true,
                    "Autenticación SSO exitosa",
                    simulatedToken);
        }

        // 🔴 Caso 4: código inválido
        return new SsoResponse(false,
                "Código de autorización inválido",
                null);
    }
}
