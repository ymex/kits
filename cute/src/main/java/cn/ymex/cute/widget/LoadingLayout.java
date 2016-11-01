package cn.ymex.cute.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import cn.ymex.cute.R;
import cn.ymex.cute.kits.Optional;


/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2015/10/31
 *
 * @author ymexc
 */
public class LoadingLayout extends FrameLayout {
    public enum Type {
        EMPTY_VIEW, ERROW_VIEW, LOADING_VIEW;
    }

    private View vEmpty, vError, vLoading;
    private boolean cancelable = true; //返回键回到内容界面
    private boolean acrossClickAble = false;//事件传递到下层，默认不能传递

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.cute, 0, 0);
        try {
            vEmpty = inflate(a.getResourceId(R.styleable.cute_lv_emptyView, R.layout.view_empty));
            vError = inflate(a.getResourceId(R.styleable.cute_lv_errorView, R.layout.view_error));
            vLoading = inflate(a.getResourceId(R.styleable.cute_lv_loadingView, R.layout.view_loading));
            cancelable = a.getBoolean(R.styleable.cute_lv_cancelable, true);
            acrossClickAble = a.getBoolean(R.styleable.cute_lv_across_click_able, false);
            backHide();
        } finally {
            a.recycle();
        }
    }

    private void noCrossClick(View view) {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private View inflate(@LayoutRes int id) {
        View view = LayoutInflater.from(getContext()).inflate(id, null);
        initRetryClick(view);
        if (!acrossClickAble) {
            noCrossClick(view);
        }
        return view;
    }

    private void backHide() {
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (vError.getVisibility() == VISIBLE) {
                        if (cancelable) {
                            vError.setVisibility(GONE);
                        }
                        if (onLoadingViewListener != null) {
                            onLoadingViewListener.dismiss(Type.ERROW_VIEW);
                        }
                        return true;
                    } else if (vEmpty.getVisibility() == VISIBLE) {
                        if (cancelable) {
                            vEmpty.setVisibility(GONE);
                        }
                        if (onLoadingViewListener != null) {
                            onLoadingViewListener.dismiss(Type.EMPTY_VIEW);
                        }
                        return true;
                    } else if (vLoading.getVisibility() == VISIBLE) {
                        if (cancelable) {
                            vLoading.setVisibility(GONE);
                        }
                        if (onLoadingViewListener != null) {
                            onLoadingViewListener.dismiss(Type.LOADING_VIEW);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initRetryClick(View view) {
        View v = view.findViewById(R.id.v_retry);
        if (v == null) {
            return;
        }
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onLoadingViewListener) {
                    onLoadingViewListener.retryClick(v);
                }
            }
        });
    }

    public void showEmpty() {
        vError.setVisibility(GONE);
        vLoading.setVisibility(GONE);
        switchover(vEmpty);
    }

    public void showError() {
        vEmpty.setVisibility(GONE);
        vLoading.setVisibility(GONE);
        switchover(vError);
    }

    public void showLoading() {
        vEmpty.setVisibility(GONE);
        vError.setVisibility(GONE);
        switchover(vLoading);
    }


    private void switchover(View view) {
        if (view == null) {
            return;
        }
        if (view.getParent() == null) {
            addView(view);
        }
        if (view.getVisibility() == VISIBLE) {
            return;
        }
        view.setVisibility(VISIBLE);
    }


    /**
     * 显示内容
     */
    public void showContent() {
        vError.setVisibility(GONE);
        vLoading.setVisibility(GONE);
        vLoading.setVisibility(GONE);
    }

    private OnLoadingViewListener onLoadingViewListener;

    public void setOnLoadingViewListener(OnLoadingViewListener onLoadingViewListener) {
        this.onLoadingViewListener = onLoadingViewListener;
    }

    public interface OnLoadingViewListener {
        void retryClick(View view);

        void dismiss(Type type);
    }


    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void setEmptyView(View view) {
        Optional.checkNull(view);
        removeExistView(vEmpty);
        this.vEmpty = view;
    }

    public void setErrorView(View view) {
        Optional.checkNull(view);
        removeExistView(vError);
        this.vError = view;
    }

    public void setLoadingView(View view) {
        Optional.checkNull(view);
        removeExistView(vLoading);
        this.vLoading = view;
    }

    private void removeExistView(View view) {
        if (view.getParent() != null) {
            this.removeView(view);
        }
    }
}
