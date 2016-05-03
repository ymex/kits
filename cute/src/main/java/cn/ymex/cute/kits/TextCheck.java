package cn.ymex.cute.kits;

import java.util.regex.Pattern;

import android.os.Build;
import android.util.Patterns;

/**
 * @Description: 字符串检查
 * @author ymex@foxmail.com
 * 
 */
public class TextCheck {
	/**
	 * @Title: subtextWithApostrophe
	 * @Description: 返回指定长度的字串并追回 省略号
	 * @param text
	 * @param wordCount
	 * @return
	 * @return: CharSequence
	 */
	public static CharSequence subtextWithApostrophe(CharSequence text,
			int wordCount) {
		StringBuilder builder = new StringBuilder(text);
		int len = builder.length();
		return len < wordCount ? builder : builder.substring(0, wordCount)
				+ "...";
	}

	/**
	 * @Title: text2num
	 * @Description:  字串转为数字
	 * @param text
	 * @param defaultValue
	 * @return
	 * @return: int
	 */
	public static int text2num(CharSequence text, int defaultValue) {
		int value = defaultValue;
		try {
			value = Integer.valueOf(text.toString());
		} catch (Exception e) {
		}
		return value;
	}

	public static float text2num(CharSequence text, float defaultValue) {
		float value = defaultValue;
		try {
			value = Float.valueOf(text.toString());
		} catch (Exception e) {
		}
		return value;
	}

	public static double text2num(CharSequence text, double defaultValue) {
		double value = defaultValue;
		try {
			value = Double.valueOf(text.toString());
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * @Description 字符串为null或空字符串，返回true
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(CharSequence text) {
		if (text == null || text.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: isEmail
	 * @Description: 邮件地址
	 * @param text
	 * @return
	 * @return: boolean
	 */
	public boolean isEmail(String text) {
		return matches(text,
				Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"));
	}

	public static boolean isIpAddress(String text) {
		return matches(
				text,
				Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
						+ "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
						+ "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
						+ "|[1-9][0-9]|[0-9]))"));
	}

	/**
	 * @Title: isWebUrl
	 * @Description:  url 地址
	 * @param text
	 * @return
	 * @return: boolean
	 */
	public static boolean isWebUrl(String text) {
		return matches(text, Build.VERSION.SDK_INT >= 8 ? Patterns.WEB_URL
				: Pattern.compile(".*"));
	}

	private static boolean matches(String text, Pattern pattern) {
		return pattern.matcher(text).matches();
	}
}
