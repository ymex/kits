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
package cn.ymex.kits;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 设备相关 （系统版本号  手机屏幕 版本号）
 */
public class Device {
    private static boolean sInitialed;
    private static Context mContext;


    public static int SCREEN_WIDTH_PX;
    public static int SCREEN_HEIGHT_PX;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;

    private static RuntimeException exception = new RuntimeException("mContext is null , pulese call Kits onCreate() in application onCreate()");

    private Device() {
    }


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
        SCREEN_WIDTH_PX = dm.widthPixels;
        SCREEN_HEIGHT_PX = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PX / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PX / dm.density);

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

    private static PackageInfo getPackageInfo(int flag) {
        if (mContext == null) {
            throw exception;
        }
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), flag);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 得到 app 版本 号
     * @return
     */
    public static int getAppVersionCode() {
        return getPackageInfo(0).versionCode;
    }

    /**
     * app 版本 名字
     * @return
     */
    public static String getAppVersionName() {
        return getPackageInfo(0).versionName;
    }

    /**
     * 系统版本名称
     * @return
     */
    public static String getOsVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 系统版本号
     * @return
     */
    public static int getOsVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获得 设备 ID
     * @return
     */
    public static String getAndroidId() {
        if (mContext == null) {
            throw exception;
        }
        String id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id;
    }

    /**
     * 设备 品牌
     * @return
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;// 手机品牌
    }

    /**
     * 设备 型号
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 设备imei
     * @return
     */
    public static String getIMEI() {
        return ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 设备 mac
     * request android.permission.ACCESS_WIFI_STATE
     * @return
     */
    public static String getMac() {
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress().replaceAll(":", "");//MAC 地址
    }
}
