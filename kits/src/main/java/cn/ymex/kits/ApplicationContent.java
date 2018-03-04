package cn.ymex.kits;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 扩展Application  增加对app 进入前后台的监听
 */
public class ApplicationContent extends Application implements Application.ActivityLifecycleCallbacks {

    private final static int F_EVENT = 0x1;
    private final static int B_EVENT = 0x2;
    private int taskCount = CheckHandler.IDLE_STATE;
    private CheckHandler handler;


    /**
     * 检测app进入前后台时间间隔
     * @return ms
     */
    public int getCheckDelay() {
        return 700;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new CheckHandler(this);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (taskCount == CheckHandler.IDLE_STATE) {
            handler.removeMessages(F_EVENT);
            handler.removeMessages(B_EVENT);
            handler.sendEmptyMessageDelayed(F_EVENT, getCheckDelay());
        }
        taskCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
        taskCount--;
        if (taskCount == CheckHandler.IDLE_STATE) {
            handler.removeMessages(F_EVENT);
            handler.removeMessages(B_EVENT);
            handler.sendEmptyMessageDelayed(B_EVENT, getCheckDelay());
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

    private static class CheckHandler extends Handler {

        private final static int IDLE_STATE = 0x0;
        private int preWhat = IDLE_STATE;
        WeakReference<ApplicationContent> appRef;


        CheckHandler(ApplicationContent applicationContent) {
            appRef = new WeakReference<>(applicationContent);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ApplicationContent app = appRef.get();
            if (app == null) {
                return;
            }
            if (msg.what == app.F_EVENT && preWhat != app.F_EVENT) {
                app.applicationDidEnterForeground();
            } else if (msg.what == app.B_EVENT) {
                app.applicationDidEnterBackground();
            }
            preWhat = msg.what;
        }
    }
}
