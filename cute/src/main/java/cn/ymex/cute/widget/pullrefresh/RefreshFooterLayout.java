package cn.ymex.cute.widget.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/1
 *
 * @author ymexc
 */
public class RefreshFooterLayout extends FrameLayout implements PushRefreshable, Refreshable {

    public RefreshFooterLayout(Context context) {
        this(context, null);
    }

    public RefreshFooterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReset() {
    }
}
