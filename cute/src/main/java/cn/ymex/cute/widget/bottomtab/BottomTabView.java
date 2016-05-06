package cn.ymex.cute.widget.bottomtab;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ymexc on 2016/5/6.
 */
public class BottomTabView extends LinearLayout implements View.OnClickListener{
    private int MIN_BOTTOMTAB_WIDTH = 64;
    private int MIDDLE_BOTTOMTAB_WIDTH = 96;
    private int MAX_BOTTOMTAB_WIDTH = 168;
    private int DP_2 = 2;
    private int DP_4 = 4;
    private int DP_6 = 6;
    private int DP_8 = 8;
    private int DP_10 = 10;
    private int DP_16 = 16;
    private int DP_12 = 12;
    private int SP_12 = 12;
    private int SP_10 = 10;
    private int SP_14 = 14;
    private int DP_24 = 24;

    private int mTextNormalColor = 000;
    private int mTextSelectColor = 000;
    private Drawable mDrawableNormal ;
    private Drawable mDrawableSelect ;
    private int mMessageTextColor = 000;
    private int mMessageTextBGColor =000;//

    private BottomTab mBottomTab;

    private ImageView mImageView;
    private TextView mTextView;
    private TextView mMessageTextView;

    public BottomTabView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public BottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        px2dp(context);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(DP_24+DP_16, DP_24+DP_6);
        frameLayout.setLayoutParams(frameLayoutParams);

        FrameLayout.LayoutParams messageParams = new FrameLayout.LayoutParams(DP_16, DP_16,Gravity.RIGHT);
        mMessageTextView = new TextView(context);
        //mMessageTextView.setPadding(DP_2,DP_2,DP_2,DP_2);
        mMessageTextView.setGravity(Gravity.CENTER);
        mMessageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SP_12);

        mMessageTextView.setLayoutParams(messageParams);
//        mMessageTextView.setBackground(getCircleShape());
        mMessageTextView.setBackgroundDrawable(getCircleShape(Color.rgb(255,0,0)));
//        mMessageTextView.setBackgroundColor(Color.rgb(255, 0, 0));
        mMessageTextView.setText("8");
        mMessageTextView.setTextColor(Color.rgb(255, 255, 255));


        FrameLayout.LayoutParams imageViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
        mImageView = new ImageView(context);
        mImageView.setLayoutParams(imageViewParams);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setColorFilter(Color.rgb(23, 43, 255));

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextView = new TextView(context);
        mTextView.setLayoutParams(textViewParams);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SP_14);
        mTextView.setTextColor(Color.rgb(23, 43, 255));
        frameLayout.addView(mImageView);
        frameLayout.addView(mMessageTextView);
        addView(frameLayout);
        addView(mTextView);
    }

    private void px2dp(Context context) {
        MIN_BOTTOMTAB_WIDTH = Toolkit.dpToPixel(context, MIN_BOTTOMTAB_WIDTH);
        MIDDLE_BOTTOMTAB_WIDTH = Toolkit.dpToPixel(context, MIDDLE_BOTTOMTAB_WIDTH);
        MAX_BOTTOMTAB_WIDTH = Toolkit.dpToPixel(context, MAX_BOTTOMTAB_WIDTH);
        DP_2 = Toolkit.dpToPixel(context, DP_2);
        DP_2 = Toolkit.dpToPixel(context, DP_4);
        DP_6 = Toolkit.dpToPixel(context, DP_6);
        DP_8 = Toolkit.dpToPixel(context, DP_8);
        DP_10 = Toolkit.dpToPixel(context, DP_10);
        DP_16 = Toolkit.dpToPixel(context, DP_16);
        DP_12 = Toolkit.dpToPixel(context, DP_12);
        DP_24 = Toolkit.dpToPixel(context, DP_24);

    }

    public void setData(BottomTab bottomTab) {
        mImageView.setImageDrawable(bottomTab.getIcon(getContext()));
        mTextView.setText(bottomTab.getTitle(getContext()));
        mTextView.setVisibility(GONE);
        mMessageTextView.setVisibility(GONE);
    }


    @Override
    public void onClick(View v) {

    }

    private ShapeDrawable getCircleShape(int color){
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(color);
        return  shapeDrawable;
    }
}
