package com.elfec.helpdesk.presenter;

import com.elfec.helpdesk.R;
import com.elfec.helpdesk.business_logic.RequirementManager;
import com.elfec.helpdesk.model.Requirement;
import com.elfec.helpdesk.model.RequirementApproval;
import com.elfec.helpdesk.presenter.views.IRequirementApprovalView;

import retrofit2.Callback;
import retrofit2.Response;

import static com.elfec.helpdesk.model.RequirementApproval.STATUS_APPROVED;
import static com.elfec.helpdesk.model.RequirementApproval.STATUS_REJECTED;

/**
 * Presenter para aprobación de requerimientos
 */
public class RequirementApprovalPresenter {

    private IRequirementApprovalView mView;
    private RequirementApproval mRequirementApproval;
    private String mRequirementId;

    public RequirementApprovalPresenter(IRequirementApprovalView view, String requirementId,
                                        RequirementApproval requirementApproval) {
        mView = view;
        mRequirementApproval = requirementApproval;
        mRequirementId = requirementId;
    }

    /**
     * Aprueba el requerimiento
     */
    public void approveRequirement() {
        mRequirementApproval.setStatus(STATUS_APPROVED);
        updateApprovalStatus(mRequirementApproval);
    }

    /**
     * Rechaza el requerimiento
     */
    public void rejectRequirement() {
        String rejectReason = mView.getRejectReason();
        if (rejectReason != null && !rejectReason.isEmpty()) {
            mRequirementApproval.setStatus(STATUS_REJECTED);
            mRequirementApproval.setRejectReason(rejectReason);
            updateApprovalStatus(mRequirementApproval);
        } else mView.setMessage(R.string.msg_no_reject_reason);
    }

    /**
     * Llama a la logica de negocio de aprobación/rechazo de requerimientos
     * @param requirementApproval request de aprobación/rechazo
     */
    private void updateApprovalStatus(RequirementApproval requirementApproval) {
        RequirementManager.updateApprovalStatus(mRequirementId, requirementApproval,
                new Callback<Requirement>() {
                    @Override
                    public void onResponse(Response<Requirement> response) {
                        mView.setMessage(R.string.msg_no_url_data);
                        mView.finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mView.setMessage(R.string.msg_no_reject_reason);
                        mView.finish();
                    }
                });
    }
}
