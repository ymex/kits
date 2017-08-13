package cn.ymex.kits;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 扩展Application  增加对app 进入前后台的监听
 */
public class AppContent extends Application implements Application.ActivityLifecycleCallbacks {
    private final int IDLE_STATE = 0x0;
    private int taskCount = IDLE_STATE;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (taskCount == IDLE_STATE) {
            applicationDidEnterForeground();
        }
        taskCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
        taskCount--;
        if (taskCount == IDLE_STATE) {
            applicationDidEnterBackground();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /**
     * app enter background
     */
    protected void applicationDidEnterBackground() {

    }

    /**
     * app enter background
     */
    protected void applicationDidEnterForeground() {

    }
}
