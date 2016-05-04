package cn.ymex.cute.samf.core;

import android.os.Bundle;

/**
 * Created by ymexc on 2016/5/4.
 * Samf fragment 操作
 */
public interface FragmentAction {
    /**
     * onely call In the SingleTop mode, if the current task stack has, then call the callback fragment
     * @param bundle
     */
    void OnNewIntent(Bundle bundle);

    /**
     * 结束fragment 并从任务栈中清出
     */
    void finish();

    /**
     * 显示fragment
     */
    void show();

    /**
     * 隐藏fragment
     */
    void hide();
}
