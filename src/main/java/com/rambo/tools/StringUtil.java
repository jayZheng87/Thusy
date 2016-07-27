package com.rambo.tools;

import java.util.UUID;

/**
 * 字符串工具类:继承org.apache.commons.lang.StringUtils
 **/
public class StringUtil extends org.apache.commons.lang.StringUtils {

    /**
      *Description:除去空字符串，并
      */
    public static String dealNull(String str) {
        return str == null ? "" : str.trim();
    }

    /**
      *Description:
      */
    public static Object dealNull(Object str) {
        return str == null ? "" : str;
    }

    public static String isNull(Object object) {
        if (object != null && object.toString().length() > 0) {
            return object.toString();
        }
        return "";
    }

    public static String getSubString(String sOurce, int len) {
        if (isEmpty(sOurce)) {
            return "";
        }
        if (sOurce.length() <= len) {
            return sOurce;
        }
        return sOurce.substring(0, len);
    }

    public static boolean booleanValue(String tfString) {
        String trimmed = tfString.trim().toUpperCase();
        return (trimmed.equals("Y")) || (trimmed.equals("true"));
    }

    /**
     * Description:获取文件后缀
     */
    public static String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
    }

    /**
     * Description:报文数据，字符串转义
     */
    public static String characterEscape(String str) {
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        // str =str.replaceAll("'","&apos;");
        // str =str.replaceAll("\r","&#xD;");
        // str =str.replaceAll(" ","&#x9");
        // str =str.replaceAll(" ","&amp;");
        return str;
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            }
            else {
                index++;
            }
        }
        return sb.toString();
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Description:获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().toString();
    }
}