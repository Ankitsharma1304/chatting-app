package common;

import java.util.Base64;

public class MessageEncryptor {
    private static final String SECRET = "key";

    public static String encrypt(String message) {
        byte[] encoded = xorWithKey(message.getBytes(), SECRET.getBytes());
        return Base64.getEncoder().encodeToString(encoded);
    }

    public static String decrypt(String encrypted) {
        byte[] decoded = Base64.getDecoder().decode(encrypted);
        return new String(xorWithKey(decoded, SECRET.getBytes()));
    }

    private static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;
    }
}
