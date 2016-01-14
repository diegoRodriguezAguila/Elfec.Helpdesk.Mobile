package com.elfec.helpdesk.presenter.views;

import android.support.annotation.StringRes;

/**
 * Abstracción de una vista de proceso
 */
public interface IProcessView<TResult> {
    /**
     * Muestra un mensaje de error al usuario. Se debería esconder la vista
     * que se haya mostrado previamente con {@link #showProcessing()}
     * @param title recurso de id string del titulo del mensaje
     * @param message el mensaje
     */
    void setError(@StringRes int title, String message);
    /**
     * Muestra un mensaje de error al usuario. Se debería esconder la vista
     * que se haya mostrado previamente con {@link #showProcessing()}
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
     * de la solicitud exitosamente. Se debería esconder la vista
     * que se haya mostrado previamente con {@link #showProcessing()}
     */
    void setResult(TResult result);
}
