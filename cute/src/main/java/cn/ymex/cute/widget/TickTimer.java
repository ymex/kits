package cn.ymex.cute.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
public class TickTimer extends TextView {

    private CountDownTimer mCountDownTimer;

    public TickTimer(Context context) {
        super(context);
    }

    public TickTimer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TickTimer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TickTimer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setTime(long millisInFuture, long interval){
        if (null != mCountDownTimer){
            mCountDownTimer.cancel();
        }
        mCountDownTimer =  new CountDownTimer(millisInFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    public void start(){
        if (null != mCountDownTimer){
            mCountDownTimer.start();
        }
    }

}
