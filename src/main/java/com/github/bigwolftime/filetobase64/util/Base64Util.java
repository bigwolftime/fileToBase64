package com.github.bigwolftime.filetobase64.util;

import java.util.Base64;

/**
 * @author liuxin
 * @date 2023/12/5 12:51
 */
public class Base64Util {

    public static String encode(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

}
