package cn.ymex.cocccute;

import android.app.Application;

import cn.ymex.cute.Cute;

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
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Cute.create(this);//实例化相关类
    }
}
