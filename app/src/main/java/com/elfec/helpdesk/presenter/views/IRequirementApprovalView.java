package com.elfec.helpdesk.presenter.views;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.elfec.helpdesk.model.Requirement;

/**
 * Abstracción de vista de aprobación de requerimientos
 */
public interface IRequirementApprovalView extends IProcessView<Requirement> {
    @Nullable
    String getRejectReason();

    /**
     * Muestra un mensaje al usuario
     * @param message message
     */
    void setMessage(@StringRes int message);
}
