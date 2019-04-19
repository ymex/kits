package cn.ymex.kits;

import android.content.DialogInterface;
import android.view.View;

/**
 * 防止重复点击
 * btn.setOnClickListener(ClickFilter.listener(new View.OnClickListener(){
 *
 * }));
 */

public class ClickFilter implements View.OnClickListener, DialogInterface.OnClickListener {
    private View.OnClickListener onClickListener;
    private DialogInterface.OnClickListener dialogClickListener;

    private long preClickTime = 0;
    private final static long DEF_TIME_OUT = 600;
    long timeOut = DEF_TIME_OUT;//毫秒

    public ClickFilter(long time, View.OnClickListener listener) {
        this.timeOut = time;
        this.onClickListener = listener;
    }

    public ClickFilter(long time, DialogInterface.OnClickListener dialogClickListener) {
        this.timeOut = time;
        this.dialogClickListener = dialogClickListener;
    }

    /**
     * @param time     点击间隔
     * @param listener 实际回调
     * @return ThrottleClicker
     */
    public static ClickFilter listener(long time, View.OnClickListener listener) {
        return new ClickFilter(time, listener);
    }

    /**
     * @param time     点击间隔
     * @param listener 实际回调
     * @return ThrottleClicker
     */
    public static ClickFilter listener(long time, DialogInterface.OnClickListener listener) {
        return new ClickFilter(time, listener);
    }

    /**
     * 默认 间隔时间 600 ms
     *
     * @param listener View.OnClickListener
     * @return ThrottleClicker
     */
    public static ClickFilter listener(View.OnClickListener listener) {
        return new ClickFilter(DEF_TIME_OUT, listener);
    }

    /**
     * 默认 间隔时间 600 ms
     *
     * @param listener View.OnClickListener
     * @return ThrottleClicker
     */
    public static ClickFilter listener(DialogInterface.OnClickListener listener) {
        return new ClickFilter(DEF_TIME_OUT, listener);
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
