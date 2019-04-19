/**
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 */
package cn.ymex.kits;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * 设备相关 （系统版本号  手机屏幕 版本号）
 */
public class Environment {
    private static boolean sInitialed;
    private static RuntimeException exception = new RuntimeException("Context is null , pulese call Kits onCreate() in application onCreate()");

    private Environment() {
    }


    private static PackageInfo getPackageInfo(int flag) {
        PackageManager packageManager = Kits.getApplication().getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(Kits.getApplication().getPackageName(), flag);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 得到 app 版本 号
     *
     * @return
     */
    public static int getVersionCode() {
        return getPackageInfo(0).versionCode;
    }

    /**
     * app 版本 名字
     *
     * @return
     */
    public static String getVersionName() {
        return getPackageInfo(0).versionName;
    }

    /**
     * 系统版本名称
     *
     * @return
     */
    public static String getOSVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 系统版本号
     *
     * @return
     */
    public static int getOSVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }


    /**
     * 设备 品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;// 手机品牌
    }

    /**
     * 设备 型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 设备imei
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        return ((TelephonyManager) Kits.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 设备 mac
     * request android.permission.ACCESS_WIFI_STATE
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getMac() {
        WifiManager wifi = (WifiManager) Kits.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress().replaceAll(":", "");//MAC 地址
    }


    /**
     * 获取app metadata
     *
     * @return
     */
    public static Bundle getAppMetaData() {

        ApplicationInfo appInfo = null;
        try {
            appInfo = Kits.getApplication().getPackageManager().getApplicationInfo(Kits.getApplication().getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return appInfo != null ? appInfo.metaData : null;
    }

}
