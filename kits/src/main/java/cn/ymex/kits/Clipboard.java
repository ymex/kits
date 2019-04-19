package cn.ymex.kits;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 剪切板
 */
public class Clipboard {

    public static ClipboardManager get(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 复制文本到剪切板
     */
    public static void copyText(Context context, String text) {
        ClipboardManager cm = get(context);
        ClipData mClipData = ClipData.newPlainText("text", text);
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText(Context context) {
        ClipboardManager clipboard = get(context);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(context);
        }
        return null;
    }
}
