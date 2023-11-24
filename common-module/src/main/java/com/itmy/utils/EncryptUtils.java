package com.itmy.utils;

import com.google.common.io.BaseEncoding;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * <p>
 * 加密工具类
 * </p>
 *
 * @author niusaibo
 * @date 2019-12-26 5:01 PM
 */
public class EncryptUtils {

	private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";

	private static final String AES_KEY = "springAesPass_#$";

	public static String getKey() {
		return AES_KEY;
	}

	public static String encodeAES(String plain) {
		return encodeAES(plain, AES_KEY);
	}

	public static String encodeAES(String plain, String key) {
		try {
			SecretKeySpec secretKeySpecification = new SecretKeySpec(getUTF8Bytes(key), "AES");
			IvParameterSpec initialVector = new IvParameterSpec(getUTF8Bytes(key));
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpecification, initialVector);
			byte[] bytes = cipher.doFinal(getUTF8Bytes(plain));
			return BaseEncoding.base64().encode(bytes);
		}
		catch (Exception var3) {
			return null;
		}
	}

	public static String decodeAES(String encoded) {
		return decodeAES(encoded, AES_KEY);
	}

	public static String decodeAES(String encoded, String key) {
		if (StringUtils.isBlank(encoded)) {
			return null;
		}
		try {
			byte[] bytes = BaseEncoding.base64().decode(encoded);
			SecretKeySpec secretKeySpecification = new SecretKeySpec(getUTF8Bytes(key), "AES");
			IvParameterSpec initialVector = new IvParameterSpec(getUTF8Bytes(key));
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpecification, initialVector);
			return new String(cipher.doFinal(bytes));
		}
		catch (Exception var3) {
			return null;
		}
	}

	private static byte[] getUTF8Bytes(String s) {
		return s.getBytes(StandardCharsets.UTF_8);
	}

	public static String encodeMD5(String message) {
		String md5str = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] input = message.getBytes();
			byte[] buff = md.digest(input);
			md5str = bytesToHex(buff);
		}
		catch (Exception var5) {
			var5.printStackTrace();
		}

		return md5str;
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();

		for (int i = 0; i < bytes.length; ++i) {
			int digital = bytes[i];
			if (digital < 0) {
				digital += 256;
			}

			if (digital < 16) {
				md5str.append("0");
			}

			md5str.append(Integer.toHexString(digital));
		}

		return md5str.toString().toUpperCase();
	}


	public static void main(String[] args) {
		String encodeAES = encodeAES("1234qwer");
		System.out.println(encodeAES);
		String decodeAES = decodeAES(encodeAES);
		System.out.println(decodeAES);

	}
}
