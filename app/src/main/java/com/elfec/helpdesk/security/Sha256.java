package com.elfec.helpdesk.security;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Se encarga de el hash SHA256
 * @author drodriguez
 *
 */
public class Sha256 {
    /**
     * This class can't be instantiated
     */
    private Sha256(){}
	/**
	 * Obtiene el hash sha256 de la cadena provista
	 * @param str str
	 * @return array de bytes equivalentes al hash
	 */
	public static byte[] hash(String str)
	{
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes(Charset.forName("UTF-8")));
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * Wrapper method for getting the hash of string as an UTF-8
     * encoded string
     * @param str str
     * @return hash of the string as a string
     */
    public static String hashString(String str){
        byte[] hash = hash(str);
        if(hash==null)
            return null;
        return new String(hash, Charset.forName("UTF-8"));
    }

	/**
	 * Wrapper method for getting the hash of string as an hex string
	 * @param str str
	 * @return hex string representing this hash's value
	 */
	public static String hexHash(String str){
		return bytesToHex(hash(str));
	}

    /**
     * Wrapper method for getting the hash of string as a Base64 encoded string
     * @param str str
     * @return Base64 encoded hash of the string
     */
	public static String base64Hash(String str){
        return Base64.encodeToString(hash(str), Base64.NO_WRAP);
	}
	
	/**
	 * Convierte un conjunto de bytes a una cadena hexadecimal
	 * @param bytes bytes
	 * @return cadena hex
	 */
	public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
