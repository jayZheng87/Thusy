package com.rambo.tools;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;

/**
 * ClassName: SecurityUtils
 * Description: （安全相关的操作方法）'
 * Create by Yet on 2017/10/11
 */
public class SecurityUtil {
    private static char[] codec_table = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /**
     * Base 64 位解密
     *
     * @param source 待解密字符串
     * @return 解密结果
     */
    public static String toBase64Decode(String source) {
        byte[] debytes = Base64.decodeBase64(source.getBytes());
        return new String(debytes);
    }

    /**
     * Base 64 加密
     *
     * @param source 待加密字符串
     * @return 加密结果
     */
    public static String toBase64Encode(String source) {
        byte[] enbytes = Base64.encodeBase64Chunked(source.getBytes());
        return new String(enbytes);
    }

    /**
     * MD5 加密字符串
     *
     * @param source
     * @return 加密后的字符串
     */
    public static String encryptMD5(String source) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(source.getBytes("UTF8"));
            byte abyte0[] = messagedigest.digest();
            return encode(abyte0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5 加密后的字符串进行校验
     *
     * @param dest   目标字符串
     * @param source 对比字符串
     * @return 对比结果
     */
    public static boolean checkMD5Str(String dest, String source) {
        if (StringUtils.isEmpty(dest) || StringUtils.isEmpty(source)) {
            return Boolean.FALSE;
        }
        return encryptMD5(dest).equals(source);
    }


    private static String encode(byte[] a) {
        int totalBits = a.length * 8;
        int nn = totalBits % 6;
        int curPos = 0;// process bits
        StringBuffer toReturn = new StringBuffer();
        while (curPos < totalBits) {
            int bytePos = curPos / 8;
            switch (curPos % 8) {
                case 0:
                    toReturn.append(codec_table[(a[bytePos] & 0xfc) >> 2]);
                    break;
                case 2:

                    toReturn.append(codec_table[(a[bytePos] & 0x3f)]);
                    break;
                case 4:
                    if (bytePos == a.length - 1) {
                        toReturn.append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);
                    } else {
                        int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
                        toReturn.append(codec_table[pos]);
                    }
                    break;
                case 6:
                    if (bytePos == a.length - 1) {
                        toReturn.append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);
                    } else {
                        int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
                        toReturn.append(codec_table[pos]);
                    }
                    break;
                default:
                    break;
            }
            curPos += 6;
        }
        if (nn == 2) {
            toReturn.append("==");
        } else if (nn == 4) {
            toReturn.append("=");
        }
        return toReturn.toString();
    }


    public static void main(String[] args) {
        try {
            System.out.println("1. MD5 加密:" + encryptMD5("123"));
            System.out.println("2. MD5 加密字符串对比:" + checkMD5Str("2017", "W2ApG7CToTsObdO17mEk1w=="));
            System.out.println("3. Base64 加密字符串:" + toBase64Encode("123301000000000,201508"));
            System.out.println("4. Base64 解密字符串:" + toBase64Decode("1fb3b66e1ddf0286c8c2d84f85f9a393f945ad1f64ffc177e2506ff1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
