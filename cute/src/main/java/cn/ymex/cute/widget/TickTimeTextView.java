package cn.ymex.cute.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p/>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 * @date 16/4/23
 * 计时器
 */
public class TickTimeTextView extends TextView{
    private CountDownTimer mTimer;
    private final static int DEFAULT_TAG = -0x11;


    public TickTimeTextView(Context context) {
        super(context);
        initView(context);
    }

    public TickTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TickTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {

    }

    /**
     * 设置时间并开始记时
     * 默认时间显示格式：00:00:00:00 天:时:分:秒
     * @param time
     */
    public synchronized void setTimer(long time) {
        setTimer(time, DEFAULT_TAG);
    }

    /**
     * 设置时间并开始记时
     * @param time 毫秒级
     * @param tag
     */
    public synchronized <T> void  setTimer(final long time, final T tag) {
        cancel();
        mTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (timeFormat != null){
                    setText(timeFormat.formtDownTime(millisUntilFinished));
                }else {
                    setText(formtDownTime(millisUntilFinished / 1000));//默认格式方法 00:00:00:00 天:时:分:秒
                }

            }
            @Override
            public void onFinish() {
                if (onTickStopListener != null) {
                    onTickStopListener.onTickStop(tag);
                }
            }
        };
        mTimer.start();
    }


    //停止计时
    public  void cancel(){
        if (mTimer!=null){
            mTimer.cancel();
        }
    }


    /**
     * 默认时间格式： 00:00:00:00 天:时:分:秒
     *
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private String formtDownTime(int day, int hour, int minute, int second) {
        char spilt = ':';
        StringBuilder builder = new StringBuilder();
        builder.append(fillZero(day)).append(spilt);
        builder.append(fillZero(hour)).append(spilt);
        builder.append(fillZero(minute)).append(spilt);
        builder.append(fillZero(second));
        return builder.toString();
    }

    private String formtDownTime(long time) {
        int second = (int) (time % 60);
        int minute = (int) (time % 3600 / 60);
        int hour = (int) (time / 3600 % 24);
        int day = (int) (time / 3600 / 24);
        return formtDownTime(day, hour, minute, second);
    }

    /**
     * 数字填充 0
     *
     * @param num
     * @return
     */
    private String fillZero(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return String.valueOf(num);
    }

    private OnTickStopListener onTickStopListener;

    public void setOnTickStopListener(OnTickStopListener onTickStopListener) {
        this.onTickStopListener = onTickStopListener;
    }

    public  interface OnTickStopListener<T> {
        public void onTickStop(T otag);
    }

    public CountDownTimer getTimer(){
        return mTimer;
    }

    private TimeFormat timeFormat;

    public void setTimeFormat(TimeFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * 时间格式化接口
     */
    public interface TimeFormat{
       public String formtDownTime(long time);
    }
}
