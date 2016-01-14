package com.elfec.helpdesk.model;

/**
 * Requirement Model
 */
public class Requirement {
    /**
     * RequirementApproval with approved status
     */
    public static final int STATUS_APPROVED = 1;
    /**
     * RequirementApproval with rejected status
     */
    public static final int STATUS_REJECTED = 0;

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

    /**
     * Verifica el estado
     *
     * @return y si esta aprobado retorna true
     */
    public boolean isApproved() {
        return status == STATUS_APPROVED;
    }

    /**
     * Verifica el estado
     *
     * @return y si esta rechazado retorna true
     */
    public boolean isRejected() {
        return status == STATUS_REJECTED;
    }

    //endregion
}
