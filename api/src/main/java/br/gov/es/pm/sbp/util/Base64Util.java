package br.gov.es.pm.sbp.util;

import java.util.Base64;

public class Base64Util {
    public static String encode(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }

    public static String decode(String s) {
        return new String(Base64.getDecoder().decode(s));
    }
}
