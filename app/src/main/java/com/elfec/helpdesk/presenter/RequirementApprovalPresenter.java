package com.elfec.helpdesk.presenter;

import com.elfec.helpdesk.R;
import com.elfec.helpdesk.presenter.views.IRequirementApprovalView;

/**
 * Presenter para aprobación de requerimientos
 */
public class RequirementApprovalPresenter {

    private IRequirementApprovalView mView;

    public RequirementApprovalPresenter(IRequirementApprovalView view) {
        mView = view;
    }

    /**
     * Aprueba el requerimiento
     */
    public void approveRequirement() {
        mView.finish();
    }

    /**
     * Rechaza el requerimiento
     */
    public void rejectRequirement() {
        String rejectReason = mView.getRejectReason();
        if (rejectReason != null && !rejectReason.isEmpty()) {
            mView.finish();
        } else mView.showMessage(R.string.msg_no_reject_reason);
    }
}
