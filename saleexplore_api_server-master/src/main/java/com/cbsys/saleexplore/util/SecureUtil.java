package com.cbsys.saleexplore.util;

import com.google.common.base.Preconditions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;


public class SecureUtil {

    private static final List<String> LEGIT_ALGORITHM = Arrays.asList("SHA-256", "MD5");

    /**
     * use sha-256 function to has an text, the output is in a Hex format
     */
    public static String getDigestData(String text, String algorithm) {
        try {
            Preconditions.checkArgument(LEGIT_ALGORITHM.contains(algorithm),
                    "illegal digest algorithm");
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return getDigest(md, text.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDigest(MessageDigest md, byte[] bytes) {
        md.update(bytes);
        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }

}
