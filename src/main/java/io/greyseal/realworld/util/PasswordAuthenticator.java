package io.greyseal.realworld.util;


import io.greyseal.realworld.exception.BadCredentialsException;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import static io.greyseal.realworld.util.PasswordManagementUtil.generateEncryptedPassword;
import static io.greyseal.realworld.util.PasswordManagementUtil.returnByteArray;

public class PasswordAuthenticator {

    public static boolean isPasswordOk(final String attemptedPassword, final String storedEncryptedPassword,
                                       final String salt) {
        byte[] computePBKDF2AttemptedPassword = null;
        try {
            computePBKDF2AttemptedPassword = generateEncryptedPassword(attemptedPassword, returnByteArray(salt));
        } catch (GeneralSecurityException genSecExc) {
            throw new BadCredentialsException();
        }
        return Arrays.equals(computePBKDF2AttemptedPassword, returnByteArray(storedEncryptedPassword));
    }

    public static void main(String[] args) {
        boolean hasCorrectPassword = isPasswordOk("saurabh",
                "8bWyuAaLG/sPMzLKIVrKKRlYPBgfzyMMKLxGFOmCKMPvneb5UE9RK1uCHDlUmscA55kv14HQ7T9P3ObPXvaJL1Lr0K3ach0woVeKSwQhBTzpbRH8FDDIPZg7HycUl/mScsm9YHsK6YyDeOV47nRx2ICWPB1jccVTUtiGh9D43Q7le6pcc6XUUeOpK1b+sN2sAisomoJIB8uBVtw6qI8/T/KwrVBIJ4pw+/dE4verO/n2hRNf/pF7GQN7TkdHJRkbTh15S0uoJY1pplApHWZT+BT2o+xjiqjdwtQiKnFhgcOYyBvGxc88NvhXyUV39pbN9mfKfsy3mZidlm6NtywxcQ==",
                "8AmsXZOlIqBBDojPUBDUuZG2E2o=");
        System.out.println(hasCorrectPassword);
    }
}