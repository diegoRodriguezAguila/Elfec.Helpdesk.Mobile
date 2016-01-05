package com.elfec.helpdesk.presenter;

import com.elfec.helpdesk.presenter.views.IRequirementApprovalView;

/**
 * Presenter para aprobación de requerimientos
 */
public class RequirementApprovalPresenter {

    private IRequirementApprovalView mView;

    public RequirementApprovalPresenter(IRequirementApprovalView view) {
        mView = view;
    }

    public void approveRequirement() {
        String rejectReason = mView.getRejectReason();
    }

    public void rejectRequirement() {

    }
}
