package com.elfec.helpdesk.security;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.elfec.helpdesk.model.security.HelpdeskApiCredential;

/**
 * Se encarga de la logica de generar credenciales de webservices
 *
 * @author drodriguez
 */
public class CredentialManager {

    private Context context;

    public CredentialManager(Context context) {
        this.context = context;
    }

    /**
     * Genera los credenciales necesarios para realizar la autenticación del usuario
     * y dispositivo con la API del servidor
     *
     * @return credenciales
     */
    public HelpdeskApiCredential generateApiCredentials() {
        SignatureManager sm = new SignatureManager(context);
        String deviceIdentifier = getDeviceIdentifier();
        return new HelpdeskApiCredential(deviceIdentifier, generateToken(deviceIdentifier, Sha256
                .hexHash(sm.getSignatureString())));
    }

    /***
     * Generates the Helpdesk Token
     *
     * @param deviceIdentifier device Id
     * @param hashedSignature  hashed app signature
     * @return generated Token
     */
    private String generateToken(String deviceIdentifier, String hashedSignature) {
        return Sha256.base64Hash(deviceIdentifier + hashedSignature);
    }

    /**
     * Obtiene la versión de la aplicación
     *
     * @return entero representando la version en el manifest
     */
    private int getVersionCode() {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException ignored) {
        }
        return -1;
    }

    /**
     * Obtiene un identificador unico para el dispositivo
     * En la mayoría de dispositivos este es el IMEI, pero
     * en aquellos dispositivos sin chip se ocupa el android Id
     *
     * @return Identificador para el dispositivo
     */
    private String getDeviceIdentifier() {
        String imei = getImei();
        if (imei == null)
            return getAndroidId();
        return imei;
    }

    /**
     * Obtiene la versión de la aplicación
     *
     * @return entero representando la version en el manifest
     */
    private String getImei() {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
    }

    /**
     * Obtiene el Android Id del dispositivo
     *
     * @return Android Id
     */
    private String getAndroidId() {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
