package cn.ymex.sample;

import cn.ymex.sample.flux.eventbus.OttoBusAdapter;
import cn.ymex.kits.ApplicationContent;
import cn.ymex.kits.Kits;

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
public class ApplicationContext extends ApplicationContent {
    @Override
    public void onCreate() {
        super.onCreate();
        Kits.create(this);//实例化相关类
        NetworkStatus.init(this);
        Kits.setFluxBusAdapter(OttoBusAdapter.get());//使用flux框架
    }

    @Override
    public int getCheckDelay() {
        return 500;
    }
}
