package com.project.utils;

import org.jboss.logging.Logger;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {
    }

    private static final Logger log = Logger.getLogger(StringUtils.class.getName());
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String BASE64_REGEX = "data:(.*);base64,(.*)";
    private static Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(final String hex) {
        return !isNullOrEmpty(hex) && emailPattern.matcher(hex).matches();
    }

    public static boolean isNullOrEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            String s1 = o.toString();
            return "".equals(s1);
        } else {
            return false;
        }
    }

    public static String joinWith(String[] array, String separator) {
        if (array == null || array.length == 0) {
            return "";
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder result = new StringBuilder();
        for (String string : array) {
            result.append(string);
            result.append(separator);
        }
        return result.toString();
    }

    public static String remNotNumChar(String s) {
        if (isNullOrEmpty(s)) {
            return "0";
        } else {
            return s.replaceAll("[^\\d]", "");
        }
    }

    public static String leftPad(String substring, String padText, int times) {
        int predictSize = substring.length() + padText.length() * times;
        StringBuilder sb = new StringBuilder(predictSize);

        for (int i = 0; i < times; i++) {
            sb.append(padText);
        }

        sb.append(substring);

        return sb.toString();
    }

    public static String leftPadWithZero(String original, int maxLength) {
        original = (original == null) ? "" : original;
        return org.apache.commons.lang3.StringUtils.leftPad(original, maxLength, '0');
    }


    public static String rightPadWithSpace(String original, int maxLength) {
        return rightPad(original, " ", maxLength);
    }

    public static String rightPad(String original, String padChar, int maxLength) {
        original = (original == null) ? "" : original;
        return org.apache.commons.lang3.StringUtils.rightPad(original, maxLength, padChar);
    }

    public static String leftPad(String substring, char padChar, int times) {
        String padText = String.valueOf(padChar);
        return leftPad(substring, padText, times);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }

        String regex = "^[0][0-9]{9,10}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }

    public static String removeAccent(String str) {

        if (str == null || str.isEmpty()) {
            return "";
        }

        String[] signs = {
                "aAeEoOuUiIdDyY",
                "áàạảãâấầậẩẫăắằặẳẵ",
                "ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ",
                "éèẹẻẽêếềệểễ",
                "ÉÈẸẺẼÊẾỀỆỂỄ",
                "óòọỏõôốồộổỗơớờợởỡ",
                "ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ",
                "úùụủũưứừựửữ",
                "ÚÙỤỦŨƯỨỪỰỬỮ",
                "íìịỉĩ",
                "ÍÌỊỈĨ",
                "đ",
                "Đ",
                "ýỳỵỷỹ",
                "ÝỲỴỶỸ"
        };

        for (int i = 1; i < signs.length; i++) {
            for (int j = 0; j < signs[i].length(); j++) {
                str = str.replace(signs[i].charAt(j), signs[0].charAt(i - 1));
            }
        }
        return str;
    }

    public static String compoundToUnicode(String str1) {
        if (isNullOrEmpty(str1)) {
            return "";
        }

        str1 = str1.replaceAll("a\u0301", "\u00E1");
        str1 = str1.replaceAll("A\u0301", "\u00C1");
        str1 = str1.replaceAll("a\u0300", "\u00E0");
        str1 = str1.replaceAll("A\u0300", "\u00C0");
        str1 = str1.replaceAll("a\u0309", "\u1EA3");
        str1 = str1.replaceAll("A\u0309", "\u1EA2");
        str1 = str1.replaceAll("a\u0303", "\u00E3");
        str1 = str1.replaceAll("A\u0303", "\u00C3");
        str1 = str1.replaceAll("a\u0323", "\u1EA1");
        str1 = str1.replaceAll("A\u0323", "\u1EA0");
        str1 = str1.replaceAll("a\u0306", "\u0103");
        str1 = str1.replaceAll("A\u0306", "\u0102");
        str1 = str1.replaceAll("a\u0306\u0301", "\u1EAF");
        str1 = str1.replaceAll("A\u0306\u0301", "\u1EAE");
        str1 = str1.replaceAll("a\u0306\u0300", "\u1EB1");
        str1 = str1.replaceAll("A\u0306\u0300", "\u1EB0");
        str1 = str1.replaceAll("a\u0306\u0309", "\u1EB3");
        str1 = str1.replaceAll("A\u0306\u0309", "\u1EB2");
        str1 = str1.replaceAll("a\u0306\u0303", "\u1EB5");
        str1 = str1.replaceAll("A\u0306\u0303", "\u1EB4");
        str1 = str1.replaceAll("a\u0306\u0323", "\u1EB7");
        str1 = str1.replaceAll("A\u0306\u0323", "\u1EB6");
        str1 = str1.replaceAll("a\u0302", "\u00E2");
        str1 = str1.replaceAll("A\u0302", "\u00C2");
        str1 = str1.replaceAll("a\u0302\u0301", "\u1EA5");
        str1 = str1.replaceAll("A\u0302\u0301", "\u1EA4");
        str1 = str1.replaceAll("a\u0302\u0300", "\u1EA7");
        str1 = str1.replaceAll("A\u0302\u0300", "\u1EA6");
        str1 = str1.replaceAll("a\u0302\u0309", "\u1EA9");
        str1 = str1.replaceAll("A\u0302\u0309", "\u1EA8");
        str1 = str1.replaceAll("a\u0302\u0303", "\u1EAB");
        str1 = str1.replaceAll("A\u0302\u0303", "\u1EAA");
        str1 = str1.replaceAll("a\u0302\u0323", "\u1EAD");
        str1 = str1.replaceAll("A\u0302\u0323", "\u1EAC");
        str1 = str1.replaceAll("e\u0301", "\u00E9");
        str1 = str1.replaceAll("E\u0301", "\u00C9");
        str1 = str1.replaceAll("e\u0300", "\u00E8");
        str1 = str1.replaceAll("E\u0300", "\u00C8");
        str1 = str1.replaceAll("e\u0309", "\u1EBB");
        str1 = str1.replaceAll("E\u0309", "\u1EBA");
        str1 = str1.replaceAll("e\u0303", "\u1EBD");
        str1 = str1.replaceAll("E\u0303", "\u1EBC");
        str1 = str1.replaceAll("e\u0323", "\u1EB9");
        str1 = str1.replaceAll("E\u0323", "\u1EB8");
        str1 = str1.replaceAll("e\u0302", "\u00EA");
        str1 = str1.replaceAll("E\u0302", "\u00CA");
        str1 = str1.replaceAll("e\u0302\u0301", "\u1EBF");
        str1 = str1.replaceAll("E\u0302\u0301", "\u1EBE");
        str1 = str1.replaceAll("e\u0302\u0300", "\u1EC1");
        str1 = str1.replaceAll("E\u0302\u0300", "\u1EC0");
        str1 = str1.replaceAll("e\u0302\u0309", "\u1EC3");
        str1 = str1.replaceAll("E\u0302\u0309", "\u1EC2");
        str1 = str1.replaceAll("e\u0302\u0303", "\u1EC5");
        str1 = str1.replaceAll("E\u0302\u0303", "\u1EC4");
        str1 = str1.replaceAll("e\u0302\u0323", "\u1EC7");
        str1 = str1.replaceAll("E\u0302\u0323", "\u1EC6");
        str1 = str1.replaceAll("i\u0301", "\u00ED");
        str1 = str1.replaceAll("I\u0301", "\u00CD");
        str1 = str1.replaceAll("i\u0300", "\u00EC");
        str1 = str1.replaceAll("I\u0300", "\u00CC");
        str1 = str1.replaceAll("i\u0309", "\u1EC9");
        str1 = str1.replaceAll("I\u0309", "\u1EC8");
        str1 = str1.replaceAll("i\u0303", "\u0129");
        str1 = str1.replaceAll("I\u0303", "\u0128");
        str1 = str1.replaceAll("i\u0323", "\u1ECB");
        str1 = str1.replaceAll("I\u0323", "\u1ECA");
        str1 = str1.replaceAll("o\u0301", "\u00F3");
        str1 = str1.replaceAll("O\u0301", "\u00D3");
        str1 = str1.replaceAll("o\u0300", "\u00F2");
        str1 = str1.replaceAll("O\u0300", "\u00D2");
        str1 = str1.replaceAll("o\u0309", "\u1ECF");
        str1 = str1.replaceAll("O\u0309", "\u1ECE");
        str1 = str1.replaceAll("o\u0303", "\u00F5");
        str1 = str1.replaceAll("O\u0303", "\u00D5");
        str1 = str1.replaceAll("o\u0323", "\u1ECD");
        str1 = str1.replaceAll("O\u0323", "\u1ECC");
        str1 = str1.replaceAll("o\u031B", "\u01A1");
        str1 = str1.replaceAll("O\u031B", "\u01A0");
        str1 = str1.replaceAll("o\u031B\u0301", "\u1EDB");
        str1 = str1.replaceAll("O\u031B\u0301", "\u1EDA");
        str1 = str1.replaceAll("o\u031B\u0300", "\u1EDD");
        str1 = str1.replaceAll("O\u031B\u0300", "\u1EDC");
        str1 = str1.replaceAll("o\u031B\u0309", "\u1EDF");
        str1 = str1.replaceAll("O\u031B\u0309", "\u1EDE");
        str1 = str1.replaceAll("o\u031B\u0303", "\u1EE1");
        str1 = str1.replaceAll("O\u031B\u0303", "\u1EE0");
        str1 = str1.replaceAll("o\u031B\u0323", "\u1EE3");
        str1 = str1.replaceAll("O\u031B\u0323", "\u1EE2");
        str1 = str1.replaceAll("o\u0302", "\u00F4");
        str1 = str1.replaceAll("O\u0302", "\u00D4");
        str1 = str1.replaceAll("o\u0302\u0301", "\u1ED1");
        str1 = str1.replaceAll("O\u0302\u0301", "\u1ED0");
        str1 = str1.replaceAll("o\u0302\u0300", "\u1ED3");
        str1 = str1.replaceAll("O\u0302\u0300", "\u1ED2");
        str1 = str1.replaceAll("o\u0302\u0309", "\u1ED5");
        str1 = str1.replaceAll("O\u0302\u0309", "\u1ED4");
        str1 = str1.replaceAll("o\u0302\u0303", "\u1ED7");
        str1 = str1.replaceAll("O\u0302\u0303", "\u1ED6");
        str1 = str1.replaceAll("o\u0302\u0323", "\u1ED9");
        str1 = str1.replaceAll("O\u0302\u0323", "\u1ED8");
        str1 = str1.replaceAll("u\u0301", "\u00FA");
        str1 = str1.replaceAll("U\u0301", "\u00DA");
        str1 = str1.replaceAll("u\u0300", "\u00F9");
        str1 = str1.replaceAll("U\u0300", "\u00D9");
        str1 = str1.replaceAll("u\u0309", "\u1EE7");
        str1 = str1.replaceAll("U\u0309", "\u1EE6");
        str1 = str1.replaceAll("u\u0303", "\u0169");
        str1 = str1.replaceAll("U\u0303", "\u0168");
        str1 = str1.replaceAll("u\u0323", "\u1EE5");
        str1 = str1.replaceAll("U\u0323", "\u1EE4");
        str1 = str1.replaceAll("u\u031B", "\u01B0");
        str1 = str1.replaceAll("U\u031B", "\u01AF");
        str1 = str1.replaceAll("u\u031B\u0301", "\u1EE9");
        str1 = str1.replaceAll("U\u031B\u0301", "\u1EE8");
        str1 = str1.replaceAll("u\u031B\u0300", "\u1EEB");
        str1 = str1.replaceAll("U\u031B\u0300", "\u1EEA");
        str1 = str1.replaceAll("u\u031B\u0309", "\u1EED");
        str1 = str1.replaceAll("U\u031B\u0309", "\u1EEC");
        str1 = str1.replaceAll("u\u031B\u0303", "\u1EEF");
        str1 = str1.replaceAll("U\u031B\u0303", "\u1EEE");
        str1 = str1.replaceAll("u\u031B\u0323", "\u1EF1");
        str1 = str1.replaceAll("U\u031B\u0323", "\u1EF0");
        str1 = str1.replaceAll("y\u0301", "\u00FD");
        str1 = str1.replaceAll("Y\u0301", "\u00DD");
        str1 = str1.replaceAll("y\u0300", "\u1EF3");
        str1 = str1.replaceAll("Y\u0300", "\u1EF2");
        str1 = str1.replaceAll("y\u0309", "\u1EF7");
        str1 = str1.replaceAll("Y\u0309", "\u1EF6");
        str1 = str1.replaceAll("y\u0303", "\u1EF9");
        str1 = str1.replaceAll("Y\u0303", "\u1EF8");
        str1 = str1.replaceAll("y\u0323", "\u1EF5");
        str1 = str1.replaceAll("Y\u0323", "\u1EF4");
        return str1;
    }

    public static String stripNonValidXMLCharacters(String in) {
        StringBuilder out = new StringBuilder(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in))) {
            return ""; // vacancy test.
        }
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if (current == 0x9 || current == 0xA || current == 0xD
                    || current >= 0x20 && current <= 0xD7FF || current >= 0xE000 && current <= 0xFFFD) {
                out.append(current);
            }

        }
        return out.toString();
    }


    public static boolean isValidBase64(String base64) {
        Matcher matcher = Pattern.compile(BASE64_REGEX).matcher(base64);
        String fileType = "";
        String data = "";
        if (matcher.find()) {
            try {
                fileType = matcher.group(1);
                data = matcher.group(2);
            } catch (Exception ignored) {
                log.error("Exception! ", ignored);
            }
        }

        return !isNullOrEmpty(fileType) && !isNullOrEmpty(data);
    }

    private static String[] arrChar = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static Random random = new Random();

    public static String genOtp() {
        StringBuilder str = new StringBuilder();
        int total = 0;
        for (int i = 0; i < 5; i++) {
            int g = random.nextInt(arrChar.length);
            str.append(arrChar[g]);
            total += g;
        }
        str.append(arrChar[total % 10]);
        return str.toString();
    }

    public static boolean stringEquals(final String s1, final String s2) {
        if ((s1 == null && "".equals(s2))
                || ("".equals(s1) && s2 == null)) {
            return false;
        }


        boolean b1 = isNullOrEmpty(s1);
        boolean b2 = isNullOrEmpty(s2);
        return b1 == b2 && (b1 || s1.equals(s2));
    }

    public static String removeLastValue(String input, String lastValue) {

        if (!isNullOrEmpty(input)) {
            input = input.trim();
            if (input.endsWith(lastValue)) {
                StringBuilder stringBuilder = new StringBuilder(input);
                stringBuilder.delete(input.lastIndexOf(lastValue), input.length());
                return stringBuilder.toString();
            }
        }
        return input;
    }

    public static void removeLastValue(StringBuilder sql, String value) {
        if (sql != null && sql.toString().endsWith(value)) {
            sql.delete(sql.toString().lastIndexOf(value), sql.length());
        }
    }

    public static boolean isValidPersonalId(String personalId) {
        if (isNullOrEmpty(personalId)) {
            return false;
        }
        return personalId.matches("[0-9]{9}|[0-9]{12}");
    }

    public static boolean isNonSpecChar(String text) {
        return text.matches("[a-zA-Z0-9 ]*");
    }

    public static boolean isAllNumber(String checkingStr) {
        String regex = "\\d+";
        return checkingStr != null && checkingStr.matches(regex);
    }

    public static boolean isAlpha(String name) {
        return name != null && name.matches("[a-zA-Z ]+");
    }


    public static String nullToDefaultString(String input, String defaultValue) {
        return isNullOrEmpty(input) ? defaultValue : input;
    }

    public static String nullToEmptyString(String input) {
        return nullToDefaultString(input, "");
    }

    public static String trimString(String input) {
        if (!isNullOrEmpty(input)) {
            return input.trim();
        }
        return input;
    }
}
