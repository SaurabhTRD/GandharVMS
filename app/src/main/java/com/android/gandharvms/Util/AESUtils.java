package com.android.gandharvms.Util;

import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AESUtils {

    private static final String KEY_ALIAS = "Indie-Care";

    // Generate or retrieve AES key securely using Android Keystore
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static SecretKey getOrCreateAesKey(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            // Check if the key already exists
            if (keyStore.containsAlias(KEY_ALIAS)) {
                // Key exists, retrieve and return it
                KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
                return entry.getSecretKey();
            } else {
                // Key does not exist, generate a new one
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .build());
                return keyGenerator.generateKey();
            }
        } catch (Exception e) {
            Log.e("EncryptionUtils", "Error generating or retrieving AES key", e);
            return null;
        }
    }

    // Encrypt plaintext password using AES/GCM encryption with the provided key
    public static String encryptPassword(String password, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] iv = cipher.getIV(); // Get IV generated by Cipher
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // Combine IV and encrypted data into a single string (IV + encryptedData)
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

            return Base64.encodeToString(combined, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e("EncryptionUtils", "Error encrypting password", e);
            return null;
        }
    }

    // Decrypt encrypted password using AES/GCM decryption with the provided key and IV
    public static String decryptPassword(String encryptedPassword, SecretKey key) {
        try {
            byte[] decodedBytes = Base64.decode(encryptedPassword, Base64.DEFAULT);

            // Split IV and encrypted data from combined byte array
            byte[] iv = new byte[12]; // GCM IV length is always 12 bytes
            byte[] encryptedBytes = new byte[decodedBytes.length - iv.length];
            System.arraycopy(decodedBytes, 0, iv, 0, iv.length);
            System.arraycopy(decodedBytes, iv.length, encryptedBytes, 0, encryptedBytes.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv); // 128-bit tag length
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.e("EncryptionUtils", "Error decrypting password", e);
            return null;
        }
    }
}
