package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    public static String generateSaltedHash(String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password, salt);

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
    }

    private static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(salt);
        return messageDigest.digest(password.getBytes());
    }

    public static boolean verifyPassword(String storedHash, String password) throws NoSuchAlgorithmException {
        String[] parts = storedHash.split(":");
        String base64Salt = parts[0];
        String base64Hash = parts[1];

        byte[] salt = Base64.getDecoder().decode(base64Salt);
        byte[] storedPasswordHash = Base64.getDecoder().decode(base64Hash);
        byte[] passwordHash = hashPassword(password, salt);

        return MessageDigest.isEqual(passwordHash, storedPasswordHash);
    }
}
