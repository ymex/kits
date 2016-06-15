package cn.ymex.cute.kits;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.

 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 * date: 16/4/20
 * 更优雅的控件查找及加载xml布局文件
 */
public class Finder {
    /**
     * Title: find
     * Description:: find view by id from view
     * @param view
     * @param id
     * @return
     * @return: T
     */


    public static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * Title: find
     * Description:: TODO find view by id from activity
     * @param act
     * @param id
     * @return
     * @return: T
     */
    public static <T extends View> T find(Activity act, int id) {
        return (T) act.findViewById(id);
    }

    /**
     * Description:加载布局文件
     * @param context
     * @param resource
     * @return
     */
    public static <T extends  View>View inflate(Context context, int resource) {
        return (T) LayoutInflater.from(context).inflate(resource, null);
    }
    /**
     * Description:加载布局文件
     * @param context
     * @param resource
     * @param viewGroup
     * @return
     */
    public static <T extends  View>View inflate(Context context, int resource, ViewGroup viewGroup) {
        return (T) LayoutInflater.from(context).inflate(resource, viewGroup);
    }
}
