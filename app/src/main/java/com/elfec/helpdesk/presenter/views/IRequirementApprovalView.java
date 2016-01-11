package com.elfec.helpdesk.presenter.views;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Abstracción de vista de aprobación de requerimientos
 */
public interface IRequirementApprovalView {
    @Nullable
    String getRejectReason();

    /**
     * Muestra un mensaje al usuario
     * @param message message
     */
    void showMessage(@StringRes int message);

    /**
     * Finaliza la vista
     */
    void finish();
}
