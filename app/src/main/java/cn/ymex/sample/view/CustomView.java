package cn.ymex.sample.view;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/4
 * <p>
 * mScroller.getCurrX() //获取mScroller当前水平滚动的位置
 * mScroller.getCurrY() //获取mScroller当前竖直滚动的位置
 * mScroller.getFinalX() //获取mScroller最终停止的水平位置
 * mScroller.getFinalY() //获取mScroller最终停止的竖直位置
 * mScroller.setFinalX(int newX) //设置mScroller最终停留的水平位置，没有动画效果，直接跳到目标位置
 * mScroller.setFinalY(int newY) //设置mScroller最终停留的竖直位置，没有动画效果，直接跳到目标位置
 * <p>
 * //滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量, duration为完成滚动的时间
 * mScroller.startScroll(int startX, int startY, int dx, int dy) //使用默认完成时间250ms
 * mScroller.startScroll(int startX, int startY, int dx, int dy, int duration)
 * <p>
 * mScroller.computeScrollOffset() //返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束。
 *
 * @author ymexc
 */


public class CustomView extends LinearLayout {

    private static final String TAG = "CustomView";

    private Scroller mScroller;
    private GestureDetectorCompat mGestureDetector;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetectorCompat(context, new CustomGestureListener());
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "get Sy" + getScrollY());
                smoothScrollTo(0, 0);
                break;
            default:
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public class CustomGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            //刚刚手指接触到触摸屏的那一刹那，就是触的那一下。
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            //手指按在触摸屏上，它的时间范围在按下起效，在长按之前。
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //手指离开触摸屏的那一刹那。
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            // 手指按在持续一段时间，并且没有松开。

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // 手指在触摸屏上迅速移动，并松开的动作。
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //手指在触摸屏上滑动。
            int dis = (int) ((distanceY - 0.5) / 2);
            Log.i(TAG, dis + ".");
            smoothScrollBy(0, dis);
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }


}