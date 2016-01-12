package com.elfec.helpdesk.helpers.ui;

import android.support.annotation.StringRes;

import com.elfec.helpdesk.R;

import java.net.ConnectException;
import java.util.HashMap;

/**
 * Helper para interpretar las excepciones a recursos de cadenas
 */
public class ExceptionInterpreter {

    private static HashMap<Class<? extends Throwable>, Integer> sRelation = new HashMap<>();

    static {
        sRelation.put(ConnectException.class, R.string.msg_connection_error);
    }

    /**
     * Interpreta la excepción especificada
     *
     * @param t excepción
     * @return recurso de cadena
     */
    public static
    @StringRes
    int interpretException(Throwable t) {
        if (t == null)
            throw new NullPointerException("Parameter Throwable can't be null");
        Integer messageId = sRelation.get(t.getClass());
        if (messageId == null)
            return R.string.msg_unexpected_error;
        return messageId;
    }
}
