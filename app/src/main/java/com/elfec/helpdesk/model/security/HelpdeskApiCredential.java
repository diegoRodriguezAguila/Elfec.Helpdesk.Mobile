package com.elfec.helpdesk.model.security;

/**
 * Credencial para la api de la mesa de ayuda
 */
public class HelpdeskApiCredential {

    private String deviceId;
    private String token;

    public HelpdeskApiCredential(String deviceId, String token) {
        this.deviceId = deviceId;
        this.token = token;
    }

//region Getters y Setters


    public String getDeviceId() {
        return deviceId;
    }

    public String getToken() {
        return token;
    }
    //endregion
}
