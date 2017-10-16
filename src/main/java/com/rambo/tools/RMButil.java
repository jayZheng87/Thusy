package com.rambo.tools;

public class RMButil {
    static String[] HanDigiStr = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    static String[] HanDiviStr = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};

    /**
     * 数字转换为大写字符串
     *
     * @param val 待转换的 double数字
     * @return String 大写数字
     */
    public static String numToRMBStr(double val) {
        String SignStr = "";
        String TailStr = "";
        if (val < 0.0D) {
            val = -val;
            SignStr = "负";
        }
        if ((val > 100000000000000.0D) || (val < -100000000000000.0D)) {
            return "数值位数过大!";
        }
        long temp = Math.round(val * 100.0D);
        long integer = temp / 100L;
        long fraction = temp % 100L;
        int jiao = (int) fraction / 10;
        int fen = (int) fraction % 10;
        if ((jiao == 0) && (fen == 0)) {
            TailStr = "整";
        } else {
            TailStr = HanDigiStr[jiao];
            if (jiao != 0) {
                TailStr = TailStr + "角";
            }
            if ((integer == 0L) && (jiao == 0)) {
                TailStr = "";
            }
            if (fen != 0) {
                TailStr = TailStr + HanDigiStr[fen] + "分";
            }
        }
        return SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;
    }

    /**
     * 将字符串数字转换为大写
     *
     * @param digitStr 需要进行转换的数字
     * @return 转换后的大写
     */
    public static String digitToString(String digitStr) {
        String[] digit = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] weight = {"", "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
        StringBuilder retDigit = new StringBuilder();
        String zs;
        String xs = "";
        int dot = digitStr.indexOf(".");
        if (dot > 0) {
            zs = digitStr.substring(0, dot);
            xs = digitStr.substring(dot + 1);
        } else
            zs = digitStr;
        int w;
        boolean flag = false;
        for (int i = 0; i < zs.length(); i++) {
            w = Integer.parseInt(zs.substring(i, i + 1));
            if (w == 0) {
                if (zs.length() - i == 5) {
                    retDigit.append("万");
                }
                if (zs.length() - i == 9) {
                    retDigit.append("亿");
                }
                flag = true;
            } else {
                if (flag) {
                    retDigit.append("零");
                    flag = false;
                }
                retDigit.append(digit[w]);
                retDigit.append(weight[(zs.length() - i)]);
            }
        }
        if (dot > 0) {
            retDigit.append("点");
            for (int i = 0; i < xs.length(); i++) {
                w = Integer.parseInt(xs.substring(i, i + 1));
                retDigit.append(digit[w]);
            }
        }
        return retDigit.toString();
    }

    private static String PositiveIntegerToHanStr(String NumStr) {
        String RMBStr = "";
        boolean lastzero = false;
        boolean hasvalue = false;

        int len = NumStr.length();
        if (len > 15) {
            return "数值过大!";
        }
        for (int i = len - 1; i >= 0; i--) {
            if (NumStr.charAt(len - i - 1) != ' ') {
                int n = NumStr.charAt(len - i - 1) - '0';
                if ((n < 0) || (n > 9)) {
                    return "输入含非数字字符!";
                }
                if (n != 0) {
                    if (lastzero) {
                        RMBStr = RMBStr + HanDigiStr[0];
                    }
                    if ((n != 1) || (i % 4 != 1) || (i != len - 1)) {
                        RMBStr = RMBStr + HanDigiStr[n];
                    }
                    RMBStr = RMBStr + HanDiviStr[i];
                    hasvalue = true;
                } else if ((i % 8 == 0) || ((i % 8 == 4) && (hasvalue))) {
                    RMBStr = RMBStr + HanDiviStr[i];
                }
                if (i % 8 == 0) {
                    hasvalue = false;
                }
                lastzero = (n == 0) && (i % 4 != 0);
            }
        }
        if (RMBStr.length() == 0) {
            return HanDigiStr[0];
        }
        return RMBStr;
    }

    public static void main(String[] args) {
        System.out.println("1.数字转换为人民币大写：" + numToRMBStr(818));
        System.out.println("2.字符串转换为人民币大写：" + digitToString("123456789012.45"));

    }
}