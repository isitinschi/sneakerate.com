package com.sneakerate.marketing;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class SignatureUtil {
    public static String computeSignature(String baseString, String keyString) {
        try {
            Mac hasher = Mac.getInstance("HmacSHA256");
            hasher.init(new SecretKeySpec(keyString.getBytes(), "HmacSHA256"));

            byte[] hash = hasher.doFinal(baseString.getBytes());

            return DatatypeConverter.printHexBinary(hash).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
