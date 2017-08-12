package cn.ymex.cuteact.widget.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.io.File;

import cn.ymex.cuteact.Cuteact;

public class WebKitView extends WebView {
    private HistoryUrlRecord historyRecordMan;
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    private WebSettings mSettings;
    private boolean reload_Flag = false;

    public WebKitView(Context context) {
        super(context);
        init(context);
    }

    public WebKitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebKitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebKitView(Context context, AttributeSet attrs, int defStyleAttr,
                      boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init(context);
    }
    private void init(Context context) {
        initHistoryRecordMan();
        this.mSettings = getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        }
        // 开启 database storage API 功能
        mSettings.setDatabaseEnabled(true);
        // 启动地理定位
        String geolocationdbPath = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        mSettings.setGeolocationEnabled(true);
        mSettings.setGeolocationDatabasePath(geolocationdbPath);
        // 开启 DOM storage API 功能
        mSettings.setDomStorageEnabled(true);
        mSettings.setBuiltInZoomControls(false);
        mSettings.setDisplayZoomControls(false);
        mSettings.setSupportZoom(false);
        mSettings.setBlockNetworkImage(false);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //去除滚动条
        this.setHorizontalScrollBarEnabled(false);
        this.setVerticalScrollBarEnabled(false);

        //判断是否联网 开启相应的加载模式
        ConnectivityManager connectivity = (ConnectivityManager) (context
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 Application Caches 功能
        mSettings.setAppCacheEnabled(true);
        mSettings.setSupportZoom(false);
        mSettings.setBuiltInZoomControls(false);
        String cacheDirPath = context.getFilesDir().getAbsolutePath()
                + APP_CACHE_DIRNAME;

        // 设置数据库缓存路径
        if (Build.VERSION.SDK_INT<= Build.VERSION_CODES.KITKAT){
            mSettings.setDatabasePath(cacheDirPath); // API 19 deprecated
        }
        // 设置Application caches缓存目录
        mSettings.setAppCachePath(cacheDirPath);
    }
    /**
     * 返回 WebSettings
     * @return
     */
    public WebSettings getWebSettings() {
        return mSettings;
    }

    /**
     * 返回url 记录器
     * @return
     */
    public HistoryUrlRecord getHistoryRecordMan() {
        return historyRecordMan;
    }


    // 初始化 历史记录器
    private void initHistoryRecordMan() {
        historyRecordMan = HistoryUrlRecord.getInstance();
    }



    public void addRecordUrl(String url) {
        historyRecordMan.addUrl(url);
    }

    /**
     * 设置已经加载
     */
    public void setReloaded() {
        this.reload_Flag = false;
    }

    @Override
    public void reload() {
        super.reload();
        reload_Flag = true;
    }

    /**
     * 得到当前页面的url
     * @return
     */
    public String getCurretnUrl() {
        return historyRecordMan.currentUrl();
    }

    public boolean isReload() {
        return reload_Flag;
    }



    public void loadUrlWithRcord(String url) {
        historyRecordMan.addUrl(url);
        loadUrl(url);
    }

    /**
     * 清除WebView缓存
     */
    public static void clearWebViewCache() {
        Context context = Cuteact.getApplication();
        // 清理WebView缓存数据库
		try {
			context.deleteDatabase("webview.db");
			context.deleteDatabase("webviewCache.db");
		} catch (Exception e) {
			e.printStackTrace();
		}

        // WebView缓存文件
        File appCacheDir = new File(context.getFilesDir().getAbsolutePath()
                + APP_CACHE_DIRNAME);

        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()
                + "/webviewCache");
        // 删除webView缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        // 删除webView缓存，缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }


    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e("error", "delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (onScrollCallBack != null) {
            int dx = l - oldl;
            int dy = t - oldt;
            onScrollCallBack.onScroll(dx, dy);
        }
    }

    private OnScrollCallback onScrollCallBack;

    public void setOnScrollCallBack(OnScrollCallback onScrollCallBack) {
        this.onScrollCallBack = onScrollCallBack;
    }

    public interface OnScrollCallback {
        public void onScroll(int dx, int dy);
    }

}
