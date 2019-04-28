package cn.ymex.kits.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;

import cn.ymex.kits.Metric;
import cn.ymex.kits.R;

public class ToolBarExt extends Toolbar {

    private TextView mTitleTextView;
    private CharSequence mTitleText;
    private int mTitleTextColor;
    private int mTitleTextAppearance;

    private int mMaxButtonHeightWidth;//最大宽度
    private int mTitleGravity = 0;//标题位置
    private boolean mPaddingStatusBar = false;

    public ToolBarExt(Context context) {
        this(context, null, R.attr.toolbarStyle);
    }

    public ToolBarExt(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public ToolBarExt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttr(context, attrs, defStyleAttr);
        if (mTitleTextView != null) {
            removeView(mTitleTextView);
        }
        setTitle(mTitleText);
        if (mPaddingStatusBar) {
            paddingStatusBar();
        }
    }

    /**
     * 填充statusbar 高度
     */
    public void paddingStatusBar() {
        int compatPadingTop = 0;
        // android 4.4以上将Toolbar添加状态栏高度的上边距，沉浸到状态栏下方
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            compatPadingTop = Metric.getStatusBarHeight(getContext());
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        setPadding(left, top + compatPadingTop, right, bottom);
    }

    private void resolveAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
        final int titleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        if (titleTextAppearance != 0) {
            setTitleTextAppearance(context, titleTextAppearance);
        }
        if (mTitleTextColor != 0) {
            setTitleTextColor(mTitleTextColor);
        }
        a.recycle();

        final TypedArray extA = context.obtainStyledAttributes(attrs, R.styleable.ToolBarExt, defStyleAttr, 0);
        mMaxButtonHeightWidth = a.getDimensionPixelSize(R.styleable.ToolBarExt_maxButtonWidth, -1);
        mTitleGravity = a.getInteger(R.styleable.ToolBarExt_titleGravity, 0);
        mPaddingStatusBar = a.getBoolean(R.styleable.ToolBarExt_paddingStatusBar, false);
        extA.recycle();

        post(new Runnable() {
            @Override
            public void run() {
                if (getLayoutParams() instanceof LayoutParams) {
                    ((LayoutParams) getLayoutParams()).gravity = Gravity.CENTER;
                }
            }
        });
    }

    private int getViewGravity(int code) {
        switch (code) {
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.START;
            default:
                return Gravity.CENTER;
        }
    }

    @Override
    public CharSequence getTitle() {
        return mTitleText;
    }

    @Override
    public void setTitle(CharSequence title) {

        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                final Context context = getContext();
                mTitleTextView = new TextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mTitleTextAppearance != 0) {
                    mTitleTextView.setTextAppearance(context, mTitleTextAppearance);
                }
                if (mTitleTextColor != 0) {
                    mTitleTextView.setTextColor(mTitleTextColor);
                }
            }
            if (mTitleTextView.getParent() != this) {
                addTitleView(mTitleTextView);
            }
        } else if (mTitleTextView != null && mTitleTextView.getParent() == this) {// 当title为空时，remove
            removeView(mTitleTextView);
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
        mTitleText = title;
    }

    private void addTitleView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }

        lp.gravity = getViewGravity(mTitleGravity);
        addView(v, lp);
    }


    @Override
    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        mTitleTextAppearance = resId;
        if (mTitleTextView != null) {
            mTitleTextView.setTextAppearance(context, resId);
        }
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        super.setNavigationIcon(icon);
        setGravityCenter();
    }

    public void setGravityCenter() {
        post(new Runnable() {
            @Override
            public void run() {
                setCenter("mNavButtonView");
                setCenter("mMenuView");
            }

        });
    }

    public ImageButton getNavigationView() {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof ImageButton) {
                return (ImageButton) getChildAt(i);
            }
        }

        return null;
    }

    private void setCenter(String fieldName) {
        try {
            Field field = getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object obj = field.get(this);
            if (obj == null) return;
            if (obj instanceof View) {
                View view = (View) obj;
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp instanceof ActionBar.LayoutParams) {
                    ActionBar.LayoutParams params = (ActionBar.LayoutParams) lp;
                    params.gravity = Gravity.CENTER;//设置居中
                    if (view instanceof ImageButton && mMaxButtonHeightWidth > 0) {
                        lp.width = mMaxButtonHeightWidth;
                    }
                    view.setLayoutParams(lp);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置返回结束页面
     *
     * @param activity
     */
    public void setNavigationBackFinish(final Activity activity) {
        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null) {
                    activity.finish();
                }
            }
        });
    }
}
