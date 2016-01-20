package com.elfec.helpdesk.security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Se encarga del manejo de firmas de la aplicación
 * 
 * @author drodriguez
 *
 */
class SignatureManager {
	
	private Context context;	

	public SignatureManager(Context context) {
		super();
		this.context = context;
	}

	/**
	 * Obtiene la firma de la aplicación en cadena Base64
	 * @return Signature
	 */
	String getSignatureString() {
		try {
            //Es necesario obtener el signature de este package
			@SuppressLint("PackageManagerGetSignatures")
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			if (info.signatures.length == 0)
				return null;
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(info.signatures[0].toByteArray());
			return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
		} catch (NameNotFoundException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
