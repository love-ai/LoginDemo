package com.ltz.mymvp.util.encrypt;

public class DUCEncryptUtil {
	public static String getEncryptString(String src, String key) {
		return Base64.encodeToString(RSA.encrypt(src.getBytes(), key), Base64.DEFAULT).trim().replaceAll("\n", "");
	}

	public static String getRsaPublicKey(String originKey) {
		return originKey.substring(27, originKey.length() - 26);
	}
}
