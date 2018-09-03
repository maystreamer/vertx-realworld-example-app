package io.greyseal.realworld.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordManagementUtil {

	public static final String PDKDF_ALGORITHM = "PBKDF2WithHmacSHA512";
	public static final int ITERATION_COUNT = 12288;
	public static final int SALT_LENGTH = 128;
	public static final int DERIVED_KEY_LENGTH = 256;

	@SuppressWarnings("null")
	public static byte[] generateEncryptedPassword(final String enteredPassword, final byte[] salt)
			throws GeneralSecurityException {
		// Strings are immutable, so there is no way to change/nullify/modify its
		// content after use. So always, collect and store security sensitive
		// information in a char array instead.
		char[] charEnteredPassword = enteredPassword.toCharArray();

		PBEKeySpec keySpec = null;

		try {
			keySpec = new PBEKeySpec(charEnteredPassword, salt, ITERATION_COUNT, DERIVED_KEY_LENGTH * 8);
		} catch (NullPointerException npe) {
			throw new GeneralSecurityException("Salt " + returnStringRep(salt) + " is null");
		} catch (IllegalArgumentException iae) {
			throw new GeneralSecurityException("One of the argument is illegal. Salt " + returnStringRep(salt)
					+ " may be of 0 length, iteration count " + ITERATION_COUNT
					+ " is not positive or derived key length " + DERIVED_KEY_LENGTH + " is not positive.");
		}

		SecretKeyFactory pbkdfKeyFactory = null;

		try {
			pbkdfKeyFactory = SecretKeyFactory.getInstance(PDKDF_ALGORITHM);
		} catch (NullPointerException nullPointExc) {
			throw new GeneralSecurityException("Specified algorithm " + PDKDF_ALGORITHM + " is null");
		} catch (NoSuchAlgorithmException noSuchAlgoExc) {
			throw new GeneralSecurityException("Specified algorithm " + PDKDF_ALGORITHM
					+ "is not available by the provider " + pbkdfKeyFactory.getProvider().getName());
		}

		byte[] pbkdfHashedArray = null;
		try {
			pbkdfHashedArray = pbkdfKeyFactory.generateSecret(keySpec).getEncoded();
		} catch (InvalidKeySpecException invalidKeyExc) {
			throw new GeneralSecurityException("Specified key specification is inappropriate");
		}

		return pbkdfHashedArray;
	}

	public static String returnStringRep(final byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	public static byte[] returnByteArray(final String data) {
		return Base64.getDecoder().decode(data);
	}
}