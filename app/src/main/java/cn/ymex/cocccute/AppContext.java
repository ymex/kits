package cn.ymex.cocccute;

import cn.ymex.cocccute.flux.eventbus.OttoBusAdapter;
import cn.ymex.cuteact.AppContent;
import cn.ymex.cuteact.Cuteact;

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
public class AppContext extends AppContent {
    @Override
    public void onCreate() {
        super.onCreate();
        Cuteact.create(this);//实例化相关类
        Cuteact.setFluxBusAdapter(OttoBusAdapter.get());//使用flux框架
    }
}
