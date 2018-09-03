package io.greyseal.realworld.util;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import static io.greyseal.realworld.util.PasswordManagementUtil.generateEncryptedPassword;
import static io.greyseal.realworld.util.PasswordManagementUtil.returnStringRep;

public class PasswordStorageUtil {
    public static int SALT_SIZE = 20; // This would be in bytes

    public static String[] encrypt(final String password) {
        byte[] salt = generateSalt();
        byte[] saltedStretchedPassword = null;
        try {
            saltedStretchedPassword = generateEncryptedPassword(password, salt);
            return new String[]{returnStringRep(saltedStretchedPassword), returnStringRep(salt)};
        } catch (GeneralSecurityException genSecExc) {
            genSecExc.printStackTrace();
        }
        return null;
    }

    private static byte[] generateSalt() {
        SecureRandom secRan = new SecureRandom();
        byte[] ranBytes = new byte[SALT_SIZE];
        secRan.nextBytes(ranBytes);
        return ranBytes;
    }

    public static void main(String[] args) {
        String[] array = encrypt("saurabh");
        for (String string : array) {
            System.out.println(string);
        }
    }
}