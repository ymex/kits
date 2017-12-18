package cn.ymex.kits;

import android.content.DialogInterface;
import android.view.View;

/**
 * 防止重复点击
 * button.setOnClickListener(ThrottleClicker.click(onclick));
 */

public class ThrottleClicker implements View.OnClickListener, DialogInterface.OnClickListener {
    View.OnClickListener onClickListener;
    DialogInterface.OnClickListener dialogClickListener;

    long preClickTime = 0;
    long timeOut = DEF_TIME_OUT;//毫秒
    private final static long DEF_TIME_OUT = 600;

    ThrottleClicker(long time, View.OnClickListener listener) {
        this.timeOut = time;
        this.onClickListener = listener;
    }

    /**
     * @param time     点击间隔
     * @param listener 实际回调
     * @return ThrottleClicker
     */
    public static ThrottleClicker click(long time, View.OnClickListener listener) {
        return new ThrottleClicker(time, listener);
    }

    /**
     * 默认 间隔时间 600 ms
     *
     * @param listener View.OnClickListener
     * @return ThrottleClicker
     */
    public static ThrottleClicker click(View.OnClickListener listener) {
        return new ThrottleClicker(DEF_TIME_OUT, listener);
    }


    @Override
    public void onClick(View view) {

        long now = System.currentTimeMillis();
        if (now - preClickTime <= timeOut) {
            return;
        }
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        preClickTime = now;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        long now = System.currentTimeMillis();
        if (now - preClickTime <= timeOut) {
            return;
        }
        if (dialogClickListener != null) {
            dialogClickListener.onClick(dialog, which);
        }
        preClickTime = now;
    }
}
