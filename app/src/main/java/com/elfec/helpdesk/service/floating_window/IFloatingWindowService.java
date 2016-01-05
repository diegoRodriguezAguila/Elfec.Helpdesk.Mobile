package com.elfec.helpdesk.service.floating_window;

/**
 * Abstracción del servicio de ventana flotante
 */
public interface IFloatingWindowService {

    /**
     * Muestra la vista de ventana flotante adecuada
     * @param view vista
     */
    void show(AbstractFloatingWindowView view);

    /**
     * Esconde la ventana
     */
    void hide();

    /**
     * Esconde la ventana y detiene el servicio
     */
    void exit();
}
