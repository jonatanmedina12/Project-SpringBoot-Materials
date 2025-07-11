package com.management.materials.dto.response;

import com.management.materials.config.AuthClient;
import com.management.materials.dto.request.UserInfo;

/**
 * DTO para la respuesta del servicio de autenticación
 */
public  class AuthValidationResponse {
    private boolean success;
    private String message;
    private UserInfo data;

    public AuthValidationResponse() {}

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public UserInfo getData() { return data; }
    public void setData(UserInfo data) { this.data = data; }
}
