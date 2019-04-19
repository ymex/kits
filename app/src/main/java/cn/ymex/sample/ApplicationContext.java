package cn.ymex.sample;

import cn.ymex.kits.AppContent;
import cn.ymex.kits.Kits;
import cn.ymex.sample.kits.Logger;

/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.

 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 * date: 16/4/24
 *
 * application 实例 在 manifest.mxl 声明
 */
public class ApplicationContext extends AppContent {
    @Override
    public void onCreate() {
        super.onCreate();
        Kits.create(this);//实例化相关类
    }

    @Override
    public int getCheckDelay() {
        return 500;
    }

    @Override
    protected void applicationDidEnterForeground() {
        super.applicationDidEnterForeground();
        Logger.log(this,"app -> applicationDidEnterForeground");
    }

    @Override
    protected void applicationDidEnterBackground() {
        super.applicationDidEnterBackground();
        Logger.log(this,"app -> applicationDidEnterBackground");
    }
}
