package com.elfec.helpdesk.presenter.views;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.elfec.helpdesk.model.Requirement;

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
    void setMessage(@StringRes int message);

    /**
     * Muestra un mensaje de error al usuario
     * @param title recurso de id string del titulo del mensaje
     * @param message el mensaje
     */
    void setError(@StringRes int title, String message);
    /**
     * Muestra un mensaje de error al usuario
     * @param title recurso de id string del titulo del mensaje
     * @param message el mensaje
     */
    void setError(@StringRes int title, @StringRes int message);

    /**
     * Muestra un mensaje de espera al usuario
     */
    void showProcessing();

    /**
     * Mensaje que se muestra cuando se finalizo el procesamiento
     * de la solicitud exitosamente
     */
    void setResult(Requirement requirement);
}
