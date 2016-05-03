package cn.ymex.cute.kits;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
/**
 * 
 * @author ymex@foxmail.com
 * 设备相关 （系统版本号  手机屏幕 版本号）
 */
public class Device {
	public static int SCREEN_WIDTH_PX;
	public static int SCREEN_HEIGHT_PX;
	public static float SCREEN_DENSITY;
	public static int SCREEN_WIDTH_DP;
	public static int SCREEN_HEIGHT_DP;
	private static boolean sInitialed;

	private static Context  mContext;

	private Device(){}

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
	
	private static RuntimeException exception = new RuntimeException("mContext is null , pulese call Cute onCreate() in application onCreate()");
	
	/** 获得 设备 ID **/
    public static  String getAndroidId() {
    	if (mContext == null ) {
    		throw exception;
		}
        String id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id;
    }
    
    /** 设备 品牌 **/
    public static String getDeviceBrand(){
    	     return android.os.Build.BRAND;// 手机品牌  
    }
    /** 设备 型号 **/
    public static String getDeviceModel(){
    	return   android.os.Build.MODEL; 
    }
    
    private static PackageInfo getPackageInfo(int flag){
    	if (mContext ==  null) {
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
    
    /** 得到 app 版本 号 **/
    public static int getAppVersionCode(){
    	return  getPackageInfo(0).versionCode;
    }
    
    /** app 版本 名字**/
    public static String getAppVersionName(){
    	return getPackageInfo(0).versionName;
    }
    
}
