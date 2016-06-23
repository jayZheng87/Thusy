package com.ramboex.tools;

/**
 * Create by Yet on 2016/6/23
 **/
public class RMButil {
    static String[] HanDigiStr = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    static String[] HanDiviStr = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};

    public static String changeToBig(double value) {
        char[] hunit = {'拾', '佰', '仟'};
        char[] vunit = {'万', '亿'};
        char[] digit = {38646, '壹', 36144, '叁', 32902, '伍', 38470, '柒', '捌', '玖'};
        long midVal = (long) (value * 100.0D);
        String valStr = String.valueOf(midVal);
        if (value < 0.1D) {
            return digit[(valStr.charAt(0) - '0')] + "分";
        }
        String head = valStr.substring(0, valStr.length() - 2);
        String rail = valStr.substring(valStr.length() - 2);

        String prefix = "";
        String suffix = "";
        if (rail.equals("00")) {
            suffix = "整";
        } else {
            suffix = digit[(rail.charAt(0) - '0')] + "角" + digit[(rail.charAt(1) - '0')] + "分";
        }
        char[] chDig = head.toCharArray();
        char zero = '0';
        byte zeroSerNum = 0;
        for (int i = 0; i < chDig.length; i++) {
            int idx = (chDig.length - i - 1) % 4;
            int vidx = (chDig.length - i - 1) / 4;
            if (chDig[i] == '0') {
                zeroSerNum = (byte) (zeroSerNum + 1);
                if (zero == '0') {
                    zero = digit[0];
                } else if ((idx == 0) && (vidx > 0) && (zeroSerNum < 4)) {
                    prefix = prefix + vunit[(vidx - 1)];
                    zero = '0';
                }
            } else {
                zeroSerNum = 0;
                if (zero != '0') {
                    prefix = prefix + zero;
                    zero = '0';
                }
                prefix = prefix + digit[(chDig[i] - '0')];
                if (idx > 0) {
                    prefix = prefix + hunit[(idx - 1)];
                }
                if ((idx == 0) && (vidx > 0)) {
                    prefix = prefix + vunit[(vidx - 1)];
                }
            }
        }
        if (prefix.length() > 0) {
            prefix = prefix + '圆';
        }
        return prefix + suffix;
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

    public static String NumToRMBStr(double val) {
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


}