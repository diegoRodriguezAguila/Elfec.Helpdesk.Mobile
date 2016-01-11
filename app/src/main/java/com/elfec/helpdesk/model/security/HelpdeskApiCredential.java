package com.elfec.helpdesk.model.security;

/**
 * Credencial para la api de la mesa de ayuda
 */
public class HelpdeskApiCredential {
    private String token;
    private String deviceId;

    public HelpdeskApiCredential(String token, String deviceId) {
        this.token = token;
        this.deviceId = deviceId;
    }

//region Getters y Setters

    public String getToken() {
        return token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    //endregion
}
