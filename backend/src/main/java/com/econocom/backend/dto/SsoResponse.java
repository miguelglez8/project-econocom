package com.econocom.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoResponse {
    private boolean success;
    private String message;
    private String accessToken;

    public SsoResponse(boolean success, String message, String accessToken) {
        this.success = success;
        this.message = message;
        this.accessToken = accessToken;
    }
}
