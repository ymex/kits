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

import android.content.Context;

import cn.ymex.cute.kits.Device;
import cn.ymex.cute.kits.UiKit;
import cn.ymex.cute.widget.notice.Toaster;
public class Cute {
    public static String TAG_E = "cute.E";
    private static Context application;

    private Cute() {

    }

    public static void create(Context context){
        application = context;
        UiKit.init(context);
        Toaster.init(context);
        Device.init(context);
    }
}
