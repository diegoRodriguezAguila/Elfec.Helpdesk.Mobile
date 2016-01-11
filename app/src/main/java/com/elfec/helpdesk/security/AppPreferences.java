package com.elfec.helpdesk.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.SoftReference;

/**
 * Maneja las sharedpreferences de toda la aplicaci�n
 *
 * @author Diego
 */
public class AppPreferences {

    private final String LOGGED_USERNAME = "logged-username";
    private final String LOGGED_USER_TOKEN = "logged-user-token";

    /**
     * Contexto
     */
    private static Context context;
    /**
     * Referencia d�bil de la instancia de appPreferences
     */
    private static SoftReference<AppPreferences> appPreferencesInstanceRef;

    private SharedPreferences preferences;

    private AppPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    /**
     * Este m�todo se debe llamar al inicializar la aplicaci�n
     *
     * @param context context
     */
    public static void init(Context context) {
        AppPreferences.context = context;
    }

    /**
     * Obtiene el contexto de la aplicaci�n
     *
     * @return el contexto de la aplicaci�n
     */
    public static Context getApplicationContext() {
        return AppPreferences.context;
    }

    public static AppPreferences instance() {
        if (appPreferencesInstanceRef == null || appPreferencesInstanceRef.get() == null) {
            if (context == null)
                throw new IllegalStateException("AppPreferences context not initialized");
            appPreferencesInstanceRef = new SoftReference<>(new AppPreferences(context));
        }
        return appPreferencesInstanceRef.get();
    }

    /**
     * Obtiene el usuario logeado actual
     *
     * @return null si es que ninguno se ha logeado
     */
    public String getLoggedUsername() {
        return preferences.getString(LOGGED_USERNAME, null);
    }

    /**
     * Asigna el usuario logeado actual, sobreescribe cualquier usuario que haya
     * sido logeado antes
     *
     * @return la instancia actual de PreferencesManager
     */
    public AppPreferences setLoggedUsername(String loggedUsername) {
        preferences.edit().putString(LOGGED_USERNAME, loggedUsername).apply();
        return this;
    }

    /**
     * Obtiene el token del usuario logeado actual
     *
     * @return null si es que ninguno se ha logeado
     */
    public String getLoggedToken() {
        return preferences.getString(LOGGED_USER_TOKEN, null);
    }

    /**
     * Asigna el token del usuario logeado actual, sobreescribe cualquier token que haya
     * sido logeado antes
     *
     * @return la instancia actual de PreferencesManager
     */
    public AppPreferences setLoggedToken(String loggedUsername) {
        preferences.edit().putString(LOGGED_USER_TOKEN, loggedUsername).apply();
        return this;
    }

    /**
     * Elimina la instancia cacheada junto con su contexto
     */
    public static void dispose() {
        context = null;
        if (appPreferencesInstanceRef != null)
            appPreferencesInstanceRef.clear();
        appPreferencesInstanceRef = null;
    }
}
