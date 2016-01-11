package com.elfec.helpdesk.security;

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
	 * Obtiene el hash sha256 de la cadena provista
	 * @param str str
	 * @return array de bytes equivalentes al hash
	 */
	public byte[] getHash(String str)
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
    public String getHashString(String str){
        return new String(getHash(str), Charset.forName("UTF-8"));
    }

	/**
	 * Wrapper method for getting the hash of string as an hex string
	 * @param str str
	 * @return hex string representing this hash's value
	 */
	public String getHexHash(String str){
		return bytesToHex(getHash(str));
	}
	
	/**
	 * Convierte un conjunto de bytes a una cadena hexadecimal
	 * @param bytes bytes
	 * @return cadena hex
	 */
	public String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
