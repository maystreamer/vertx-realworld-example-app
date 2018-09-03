package io.greyseal.realworld.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class CryptoUtil {
	private static Cipher cipher;
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	public static SecretKey generateKey() {
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			return kgen.generateKey();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String encodedKey() {
		return Base64.getEncoder().encodeToString(generateKey().getEncoded());
	}

	public static byte[] decodedKey(final String encodedKey) {
		return Base64.getDecoder().decode(encodedKey);
	}

	public static SecretKey originalKey(final String encodedKey) {
		byte[] decodedKey = decodedKey(encodedKey);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}

	public static String encrypt(SecretKey skey, String plaintext) {
		try {
			byte[] ciphertext = null;
			final int blockSize = cipher.getBlockSize();
			byte[] initVector = new byte[blockSize];
			(new SecureRandom()).nextBytes(initVector);
			IvParameterSpec ivSpec = new IvParameterSpec(initVector);
			cipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);
			byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
			ciphertext = new byte[initVector.length + cipher.getOutputSize(encoded.length)];
			for (int i = 0; i < initVector.length; i++) {
				ciphertext[i] = initVector[i];
			}
			cipher.doFinal(encoded, 0, encoded.length, ciphertext, initVector.length);
			return new String(ciphertext);
		} catch (InvalidAlgorithmParameterException | ShortBufferException | BadPaddingException
				| IllegalBlockSizeException | InvalidKeyException e) {
			throw new IllegalStateException(e.toString());
		}
	}

	public static String decrypt(SecretKey skey, byte[] ciphertext)
			throws BadPaddingException, IllegalBlockSizeException {
		try {
			final int blockSize = cipher.getBlockSize();
			byte[] initVector = Arrays.copyOfRange(ciphertext, 0, blockSize);
			IvParameterSpec ivSpec = new IvParameterSpec(initVector);
			cipher.init(Cipher.DECRYPT_MODE, skey, ivSpec);
			byte[] plaintext = cipher.doFinal(ciphertext, blockSize, ciphertext.length - blockSize);
			return new String(plaintext);
		} catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
			throw new IllegalStateException(e.toString());
		}
	}
}
