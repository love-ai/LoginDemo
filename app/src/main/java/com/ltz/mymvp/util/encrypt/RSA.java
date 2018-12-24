package com.ltz.mymvp.util.encrypt;

import android.annotation.SuppressLint;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {

	public static final String KEY_ALGORITHM = "RSA";

	@SuppressLint("TrulyRandom")
	public static byte[] encrypt(byte[] data, String pubKey) {
		PublicKey key = null;
		try {
			key = getPublicKeyFromString(pubKey);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		byte[] cipherData = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherData = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherData;
	}

	private static PublicKey getPublicKeyFromString(String pubkey) throws Exception {
		byte[] pubKeyBytes = Base64.decode(pubkey, Base64.DEFAULT);
		PublicKey publicKey = null;

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			publicKey = keyFactory.generatePublic(x509KeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publicKey;
	}
}
