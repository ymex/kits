/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 * date: 16/4/21
 */
package cn.ymex.cute;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import cn.ymex.cute.kits.Device;
import cn.ymex.cute.kits.Optional;
import cn.ymex.cute.kits.Storage;
import cn.ymex.cute.kits.ViewKit;
import cn.ymex.cute.mode.flux.Flux;
import cn.ymex.cute.widget.notice.Toaster;

public final class Cute {
    public static String TAG_E = "cute.E";
    private static Context application;

    private Cute() {
        throw new RuntimeException("Cute not allow instance");
    }

    public static void create(Context context){
        all(context);
    }

    public static void all(Context context) {
        application = context;
        ViewKit.init(context);
        Storage.init(context);
        Toaster.init(context);
        Device.init(context);
    }

    /**
     * 使用flux 模式开发app
     * @param busAdapter
     */
    public static void setFluxBusAdapter(@NonNull Flux.BusAdapter busAdapter) {
        Flux.instance().setBusAdapter(busAdapter);
    }

    public static  Application getApplication() {
        Optional.checkNull(application,"application is null!");
        return (Application) application;
    }

}
