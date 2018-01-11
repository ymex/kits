package cn.ymex.kits;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by ymexc on 2018/1/11.
 * About:tools for android
 */

public class ToolKit {
    /**
     * 复制文本到剪切板
     * @param context context
     * @param text text
     * @return
     */
    public static boolean copyToClipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", text);
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
            return true;
        }
        return false;
    }

    /**
     * 关闭软键盘
     *
     * @param context context
     * @param editText editText
     */
    public static boolean closeKeybord(Context context,EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }
}
