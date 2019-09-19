package com.mtp.simplecoding;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.util.ArrayList;
import java.util.Iterator;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.mtp.simplecoding.Utility.chknull;


public class CipherUtils {
	//old encryption key(128 bit)
	//private final static String key = "OhMMqdnwoODRD1UQG3jVAA==";
	//private final static String initVector = "EfdC2Q85bT93DxYcXymDbQ==";

	//new encryption key(256 bit)
	private final static String key = "GQEFCAkHBBcGBVkEAQVKIAUIIg86Sx0YGRobHAUHBQE=";
	private final static String initVector = "EfdC2Q85bT93DxYcXymDbQ==";

	@SuppressLint("TrulyRandom")
	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(Base64.decode(initVector, Base64.DEFAULT));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encodeToString(encrypted, Base64.DEFAULT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(Base64.decode(initVector, Base64.DEFAULT));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String[] decrypt(String[] encryptedData) {
		for (int i = 0; i < encryptedData.length; i++) {
			String data = encryptedData[i];
			if (chknull(data,"").length()>0)
				data = decrypt(data);
			encryptedData[i] = data;
		}
		return encryptedData;
	}

	public static String[] encrypt(String[] plainData) {
		for (int i = 0; i < plainData.length; i++) {
			String data = plainData[i];
			if (chknull(data,"").length()>0)
				data = encrypt(data);
			plainData[i] = data;
		}
		return plainData;
	}

	public static ArrayList<String> cipher(ArrayList<String> listOfValues) {
		ArrayList<String> listOfEncryptedValues = new ArrayList<String>(listOfValues.size());
		for (Iterator<String> iterator = listOfValues.iterator(); iterator.hasNext();) {
			String data = (String) iterator.next();
			String encryptedData = encrypt(data);
			listOfEncryptedValues.add(encryptedData);
		}
		return listOfEncryptedValues;
	}

}
