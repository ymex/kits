package cn.ymex.kits;

import android.view.View;

/**
 * 防止重复点击
 * button.setOnClickListener(ThrottleClicker.click(onclick));
 */

public class ThrottleClicker implements View.OnClickListener {
    View.OnClickListener onClickListener;
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
     * @return
     */
    public static ThrottleClicker click(long time, View.OnClickListener listener) {
        return new ThrottleClicker(time, listener);
    }

    /**
     * 默认 间隔时间 600 ms
     *
     * @param listener
     * @return
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
}
