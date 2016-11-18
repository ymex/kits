/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 */
package cn.ymex.cute.kits;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by ymexc on 2016/6/16.
 */
public class ViewKit {
    private static boolean sInitialed;
    private static Context mContext;

    public static int SCREEN_WIDTH_PX;
    public static int SCREEN_HEIGHT_PX;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;


    public static void init(Context context) {
        if (sInitialed || context == null) {
            throw new IllegalArgumentException("context not allow null");
        }
        mContext = context;
        sInitialed = true;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        ViewKit.SCREEN_WIDTH_PX = dm.widthPixels;
        ViewKit.SCREEN_HEIGHT_PX = dm.heightPixels;
        ViewKit.SCREEN_DENSITY = dm.density;
        ViewKit.SCREEN_WIDTH_DP = (int) (ViewKit.SCREEN_WIDTH_PX / dm.density);
        ViewKit.SCREEN_HEIGHT_DP = (int) (ViewKit.SCREEN_HEIGHT_PX / dm.density);
    }

    /**
     * @param dp to px
     * @return
     */
    public static int dp2px(float dp) {
        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public static int designedDP2px(float designedDp) {
        // density = 160 时 w=320 * h 480 1dp = 1px
        if (SCREEN_WIDTH_DP != 320) {
            designedDp = designedDp * SCREEN_WIDTH_DP / 320f;
        }
        return dp2px(designedDp);
    }

    public static float px2dp(int px) {
        final float scale = SCREEN_DENSITY;
        return (px / scale + 0.5f);
    }

    public static void setPadding(final View view, float left, float top,
                                  float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right),
                dp2px(bottom));
    }

    /**
     * find view by id from view
     *
     * @param view
     * @param id
     * @return
     * @return: T
     */

    public static <T extends View> T find(@NonNull View view, @IdRes int id) {
        Optional.checkNull(view);
        return (T) view.findViewById(id);
    }

    /**
     * find view by id from activity
     *
     * @param act
     * @param id
     * @return
     * @return: T
     */
    public static <T extends View> T find(@NonNull Activity act, @IdRes int id) {
        Optional.checkNull(act);
        return (T) act.findViewById(id);
    }

    /**
     * find view and set click event for it
     *
     * @param view
     * @param id
     * @param listener
     */
    public static <T extends View> T findClick(@NonNull View view, @IdRes int id, View.OnClickListener listener) {
        T t = find(view, id);
        t.setOnClickListener(listener);
        return t;
    }

    /**
     * find view and set click event for it
     *
     * @param act
     * @param id
     * @param listener
     */
    public static <T extends View> T findClick(@NonNull Activity act, @IdRes int id, View.OnClickListener listener) {
        T t = find(act, id);
        t.setOnClickListener(listener);
        return t;
    }


    /**
     * inflate xml layout
     *
     * @param context
     * @param resource
     * @return
     */
    public static <T extends View> View inflate(@NonNull Context context, @LayoutRes int resource) {
        return (T) inflate(context, resource, null, false);
    }

    /**
     * inflate xml layout
     *
     * @param context
     * @param resource
     * @param viewGroup
     * @return
     */
    public static <T extends View> View inflate(@NonNull Context context, @NonNull int resource, ViewGroup viewGroup) {
        return (T) inflate(context, resource, viewGroup, false);
    }

    public static <T extends View> View inflate(@NonNull Context context, @NonNull int resource, ViewGroup viewGroup, boolean flag) {
        return (T) LayoutInflater.from(context).inflate(resource, viewGroup, flag);
    }

}
