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
package cn.ymex.kits;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import cn.ymex.kits.mode.flux.Flux;
import cn.ymex.kits.widget.Toaster;

public final class Kits {
    public static String TAG_E = "kits.E";
    private static Context application;

    private Kits() {
        throw new RuntimeException("Kits not allow instance");
    }

    public static void create(Context context) {
        all(context);
    }

    public static void all(Context context) {
        if (application instanceof Application) {
            application = context;
        }else {
            application = context.getApplicationContext();
        }
        Storage.init(application);
        Toaster.init(application);
        Device.init(application);
    }

    /**
     * 使用flux 模式开发app
     *
     * @param busAdapter
     */
    public static void setFluxBusAdapter(@NonNull Flux.BusAdapter busAdapter) {
        Flux.instance().setBusAdapter(busAdapter);
    }

    public static Application getApplication() {
        Optional.checkNull(application, "application is null!");
        return (Application) application;
    }

}
