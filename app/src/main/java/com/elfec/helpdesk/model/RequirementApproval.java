package com.elfec.helpdesk.model;

/**
 * Modelo de aprobación/rechazo de requerimientos
 */
public class RequirementApproval {

    /**
     * RequirementApproval with approved status
     */
    public static final String STATUS_APPROVED = "Approved";
    /**
     * RequirementApproval with rejected status
     */
    public static final String STATUS_REJECTED = "Rejected";

    private String status;
    private String userCode;
    private String requestUser;
    private String rejectReason;

    public RequirementApproval(){}

    /**
     * Constructor for RequirementApproval
     * @param userCode user code
     * @param requestUser requester user
     */
    public RequirementApproval(String userCode, String requestUser) {
        this.userCode = userCode;
        this.requestUser = requestUser;
    }

    //region Getters Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status==null || !status.equals(STATUS_APPROVED)
                || !status.equals(STATUS_REJECTED))
            throw new IllegalArgumentException("Invalid status value");
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
