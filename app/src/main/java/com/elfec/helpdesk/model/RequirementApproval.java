package com.elfec.helpdesk.model;

/**
 * Modelo de aprobación/rechazo de requerimientos
 */
public class RequirementApproval {

    private String status;
    private String userCode;
    private String requestUser;
    private String rejectReason;


    //region Getters Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    //endregion
}
