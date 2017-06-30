package com.mihai.licenta.Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by mihai on 16.05.2017.
 */
public class Util {

    public static Date getSqlDate() {
        return new Date(Calendar.getInstance().getTimeInMillis());
    }


    final protected static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String sha1Hash(String toHash) {
        String hash = null;
        if (toHash != null && !toHash.isEmpty()) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                byte[] bytes = toHash.getBytes("UTF-8");
                digest.update(bytes, 0, bytes.length);
                bytes = digest.digest();
                hash = bytesToHex(bytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return hash;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
