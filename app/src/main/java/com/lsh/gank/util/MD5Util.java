package com.lsh.gank.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Li on 2016/10/29.
 */

public class MD5Util {

    public static String md5(String s) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(s.getBytes());

            for (byte b : digest) {
                String str = Integer.toHexString(b & 0xff);
                if (str.length() == 1)
                    str = 0 + str;
                sb.append(str);
            }

        } catch (NoSuchAlgorithmException e) {

        }
        return sb.toString();
    }


}
