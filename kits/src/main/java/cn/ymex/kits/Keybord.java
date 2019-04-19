package cn.ymex.kits;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘
 */
public class Keybord {
    /**
     * 关闭软键盘
     *
     * @param context context
     * @param view    view
     */
    public static boolean close(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

}
