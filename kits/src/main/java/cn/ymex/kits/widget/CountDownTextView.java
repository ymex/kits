/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 */
package cn.ymex.kits.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;


/**
 * 文本计时器控件
 */
public class CountDownTextView extends android.support.v7.widget.AppCompatTextView {
    private CountDownTimer mTimer;
    private final static int DEFAULT_TAG = -0x11;
    private boolean countDownIng = false;
    private boolean stopNormal = true;

    public CountDownTextView(Context context) {
        super(context);
        initView(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {

    }

    /**
     * 设置时间
     *
     * @param time 单位：毫秒
     */
    public synchronized void setTimer(long time) {
        setTimer(time, 1000, DEFAULT_TAG);
    }

    /**
     * @param time
     * @param tag
     */
    public synchronized void setTimer(final long time, final long countDownInterval, final int tag) {
        if (mTimer != null) {
            mTimer.cancel();
        }
        countDownIng = true;
        stopNormal = true;
        mTimer = new CountDownTimer(time, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (timeFormat != null) {
                    setText(timeFormat.formtDownTime(millisUntilFinished));
                } else {
                    setText(formtDownTime(millisUntilFinished / 1000));//默认格式方法
                }

            }

            @Override
            public void onFinish() {
                countDownIng = false;
                if (onTickStopListener != null) {
                    onTickStopListener.onTickStop(tag, null, stopNormal);
                }
            }
        };
        mTimer.start();
    }

    public boolean isCountDownIng() {
        return countDownIng;
    }

    /**
     * 销毁计时器
     */
    public void destroyTimer() {
        countDownIng = false;
        setOnTickStopListener(null);
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    /**
     * 主动停止计时
     */
    public void stopCountDown() {
        if (mTimer != null) {
            countDownIng = false;
            stopNormal = false;
            mTimer.cancel();
            mTimer.onFinish();
        }
    }


    /**
     * 返回时间格式
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
    private String fillZero(long num) {

        if (num < 10) {
            return "0" + num;
        }
        return String.valueOf(num);
    }

    private OnTickStopListener onTickStopListener;

    public void setOnTickStopListener(OnTickStopListener onTickStopListener) {
        this.onTickStopListener = onTickStopListener;
    }

    public interface OnTickStopListener {
        /**
         * 计时停止回调
         *
         * @param itag
         * @param otag
         * @param normalStop
         */
        void onTickStop(int itag, Object otag, boolean normalStop);
    }

    public CountDownTimer getTimer() {
        return mTimer;
    }

    private TimeFormat timeFormat;

    public void setTimeFormat(TimeFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * 时间格式化接口
     */
    public interface TimeFormat {
        String formtDownTime(long time);
    }
}
