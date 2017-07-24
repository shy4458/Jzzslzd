package com.bksx.jzzslzd.net;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 数据加密
 * 
 * @author Administrator
 * 
 */
public class DESUtil {
	private Cipher cipher;
	private static Key key = null;
	private static IvParameterSpec iv = null;

	public byte[] encrypt(byte[] message) {
		try {
			this.cipher.init(1, key, iv);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		try {
			return this.cipher.doFinal(message);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			return null;
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] decryption(byte[] message) {
		try {
			this.cipher.init(2, key, iv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		try {
			return this.cipher.doFinal(message);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			return null;
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void init(String skey, String siv) {
		try {
			DESKeySpec dks = new DESKeySpec(skey.getBytes("UTF-8"));

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			key = keyFactory.generateSecret(dks);
			iv = new IvParameterSpec(siv.getBytes("UTF-8"));

			this.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}