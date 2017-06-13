package com.rambo.tools;

import java.util.UUID;

/**
 * 字符串工具类:继承org.apache.commons.lang.StringUtils
 **/
public class StringUtil extends org.apache.commons.lang.StringUtils {

    /**
     * Description:除去空字符串，并消除两边空格
     */
    public static String dealNull(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * Description:除去空字符串，并消除两边空格
     */
    public static Object dealNull(Object str) {
        return str == null ? "" : str;
    }

    /**
     * Description:将对象转化为字符串，用于特殊场景
     */
    public static String getStr(Object object) {
        if (object != null && object.toString().length() > 0) {
            return object.toString();
        }
        return "";
    }

    /**
     * Description:截取指定长度的字符串
     * 与字符串 substring 方法相比，规避空字符串，长度不够截取等问题
     */
    public static String getSubString(String sOurce, int len) {
        if (isEmpty(sOurce)) {
            return "";
        }
        if (sOurce.length() <= len) {
            return sOurce;
        }
        return sOurce.substring(0, len);
    }

    /**
     * Description:字符串转换为 boolean
     */
    public static boolean booleanValue(String tfString) {
        String trimmed = tfString.trim().toUpperCase();
        return (trimmed.equals("Y")) || (trimmed.equals("true"));
    }


    /**
     * Description:去除字符串所有空格
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * Description:字符串是否有长度
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Description:获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 字符串首字母小写
     *
     * @param name 待处理字符串
     */
    public static String firstLetterLowercase(String name) {
        if (isEmpty(name)) {
            return null;
        }
        return lowerCase(name.substring(0, 1)) + name.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param name 待处理字符串
     */
    public static String firstLetterUppercase(String name) {
        if (isEmpty(name)) {
            return null;
        }
        return upperCase(name.substring(0, 1)) + name.substring(1);
    }

    public static void main(String[] args) {
        String out = " hello word! String ";
        Object out1 = " hello word!  Object ";

        System.out.println("1. 去除空格和空字符串：" + dealNull(out));
        System.out.println("2. 去除空格和空字符串：" + dealNull(out1));
        System.out.println("3. 对象转字符串：" + getStr(out1));
        System.out.println("4. 截取字符串：" + getSubString(out, 36));
        System.out.println("5. 字符串转换为 boolean：" + booleanValue("Y"));
        System.out.println("6. 字符串是否有长度:" + hasLength(out));
        System.out.println("7. 去除字符串所有空格:" + trimAllWhitespace(getStr(out1)));
        System.out.println("8. 获取UUID：" + getUUID());
        System.out.println("9. 首字母小写：" + firstLetterLowercase("VFFFFF"));
        System.out.println("10. 首字母大写：" + firstLetterUppercase("vFFFFF"));

    }
}