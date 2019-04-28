package cn.ymex.kits;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 手机屏幕及像素相关
 */
public class Metric {

    private static DisplayMetrics DISPLAYMETRICS = Resources.getSystem().getDisplayMetrics();


    public static DisplayMetrics get() {
        return DISPLAYMETRICS;
    }

    /**
     * 屏幕高度
     *
     * @return px
     */
    public static int getScreenHeight() {
        return DISPLAYMETRICS.heightPixels;
    }

    /**
     * 屏幕宽度
     *
     * @return px
     */
    public static int getScreenWidth() {
        return DISPLAYMETRICS.widthPixels;
    }

    /**
     * dp to px
     *
     * @param dp dip
     * @return int
     */
    public static int dip2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px to dip
     *
     * @param px px
     * @return float
     */
    public static float px2dip(int px) {
        return (px / DISPLAYMETRICS.density + 0.5f);
    }



    /**
     * 获取状态栏高度
     *
     * @return px
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
