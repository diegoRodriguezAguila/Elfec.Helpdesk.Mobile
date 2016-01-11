package com.elfec.helpdesk.model;

/**
 * Requirement Model
 */
public class Requirement {
    private String code;
    private short status;

    //region Getters Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    //endregion
}
