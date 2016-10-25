package com.rambo.tools;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * ClassName: SecurityUtils
 * Description: （安全相关的操作方法）
 */
public class SecurityUtil {
	private static char[] codec_table = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	/** 
	* Description: Base 64 位解密
	* eg: decodeStr("NjMwMTA0MDEyMTEwMDAyMDM5LDIwMTUwNg==")
	*/
	public static String toBase64Decode(String pwd) {
		byte[] debytes = Base64.decodeBase64(new String(pwd).getBytes());
		return new String(debytes);
	}

	/** 
	* Description: Base 64 加密
	* eg:encodeStr("630104012110002039,201506")
	*/
	public static String toBase64Encode(String pwd) {
		byte[] enbytes = Base64.encodeBase64Chunked(pwd.getBytes());
		return new String(enbytes);
	}

	/** 
	* Description: MD5 加密字符串
	* eg: encryptMD5("nxccsoft2015")
	*/
	public static String encryptMD5(String s) throws Exception {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(s.getBytes("UTF8"));
			byte abyte0[] = messagedigest.digest();
			return encode(abyte0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获得密文时出错！");
		}
	}

	/** 
	* Description: MD5 加密后的字符串进行对比
	* eg:   checkMD5Str("nxccsoft2015", "1OrGLHIjO1wQJO+sXrM2gg==")
	*/
	public static boolean checkMD5Str(String s, String s1) throws Exception {
		return encryptMD5(s).equals(s1);
	}
	
    /** 
    * Description: 自用 base64加密
    */
    public static String encode(byte[] a) {   
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
                    toReturn   
                            .append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);   
                } else {   
                    int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;   
                    toReturn.append(codec_table[pos]);   
                }   
                break;   
            case 6:   
                if (bytePos == a.length - 1) {   
                    toReturn   
                            .append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);   
                } else {   
                    int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;   
                    toReturn.append(codec_table[pos]);   
                }   
                break;   
            default:   
                break;   
            }   
            curPos+=6;   
        }   
        if(nn==2)   
        {   
            toReturn.append("==");   
        }   
        else if(nn==4)   
        {   
            toReturn.append("=");   
        }   
        return toReturn.toString();   
    }

	public static void main(String[] args) {
		try {
			System.out.println(encryptMD5("nxccsoft2015"));
			System.out.println(checkMD5Str("nxccsoft2015", "1OrGLHIjO1wQJO+sXrM2gg=="));
			System.out.println(toBase64Encode("123301000000000,201508"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
