package org.airpenthouse.GoTel.util;

import java.util.Base64;

public class TokenEncryptor {

    public static String encoder(String originalString) {
        // Encoding
        String encodedString = Base64.getEncoder().encodeToString(originalString.getBytes());
        Log.info("Encoded String: " + encodedString);
        return encodedString;
    }

    public static String decoder(String encodedString) {
        // Decoding
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        Log.info("Decoded String: " + decodedString);
        return decodedString;
    }

}
