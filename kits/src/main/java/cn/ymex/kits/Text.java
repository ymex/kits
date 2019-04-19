/*
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 */
package cn.ymex.kits;

import java.util.regex.Pattern;

import android.os.Build;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.util.Patterns;

/**
 *
 */
public class Text {

    /**
     * 拼接字符串
     * @param texts 可变参数
     * @return  String
     */
    public static String append(CharSequence... texts) {
        if (texts.length <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (CharSequence t : texts) {
            if (isEmpty(t)) {
                continue;
            }
            builder.append(t);
        }
        return builder.toString();
    }


    /**
     * 符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence text) {
        return Empty.isEmpty(text);
    }

    /**
     * Returns the String value of the subsequence from the start index with suffix
     *
     * @param text
     * @param wordCount
     * @return
     */
    public static CharSequence substring(CharSequence text, int wordCount, CharSequence suffix) {
        StringBuilder builder = new StringBuilder(text);
        int len = builder.length();
        return len < wordCount ? builder : builder.substring(0, wordCount) + suffix;
    }



    /**
     * 字串转为数字
     *
     * @param text 要转换的字串
     * @param defaultValue 默认值
     * @return int
     */
    public static int toNum(CharSequence text, int ... defaultValue) {
        int def = 0;
        if (defaultValue.length > 0) {
            def = defaultValue[0];
        }
        if (isEmpty(text)) {
            return def;
        }

        try {
            def = Integer.valueOf(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static float toNum(CharSequence text, float ... defaultValue) {
        float def = 0f;
        if (defaultValue.length > 0) {
            def = defaultValue[0];
        }
        if (isEmpty(text)) {
            return def;
        }

        try {
            def = Float.valueOf(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static long toNum(CharSequence text, long ... defaultValue) {
        long def = 0L;
        if (defaultValue.length > 0) {
            def = defaultValue[0];
        }
        if (isEmpty(text)) {
            return def;
        }

        try {
            def = Long.valueOf(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static double toNum(CharSequence text, double ... defaultValue) {

        double def = 0d;
        if (defaultValue.length > 0) {
            def = defaultValue[0];
        }
        if (isEmpty(text)) {
            return def;
        }

        try {
            def = Double.valueOf(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static boolean toBool(CharSequence text, boolean ...  defaultValue) {
        boolean def = false;
        if (defaultValue.length > 0) {
            def = defaultValue[0];
        }
        if (isEmpty(text)) {
            return def;
        }

        try {
            def = Boolean.valueOf(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * isEmpty(value) ? defvalue : value;
     *
     * @param value 判断
     * @param defvalue 默认
     * @return
     */

    public static CharSequence or(final CharSequence value, CharSequence defvalue) {
        return isEmpty(value) ? defvalue : value;
    }

    /**
     * Title: isEmail
     * Description: 邮件地址
     *
     * @param text 字串
     * @return boolean
     */
    public boolean isEmail(String text) {
        return matches(text,
                Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"));
    }

    public static boolean isIp(String text) {
        return matches(
                text,
                Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9]))"));
    }

    /**
     * web url
     *
     * @param text url
     * @return boolean
     */
    public static boolean isWebUrl(String text) {
        return matches(text, Build.VERSION.SDK_INT >= 8 ? Patterns.WEB_URL
                : Pattern.compile(".*"));
    }

    private static boolean matches(String text, Pattern pattern) {
        return pattern.matcher(text).matches();
    }
}
