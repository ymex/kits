package cn.ymex.kits;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/18
 *
 * @author ymexc
 */
public class Finder {

    private Object hostwindow;


    private Finder() {
        super();
    }
    private Finder(Object obj) {
        this.hostwindow =obj;
    }

    public static Finder builder(Activity activity) {
        return new Finder(activity);
    }

    public static Finder builder(Fragment fragment) {
        return new Finder(fragment.getView());
    }

    public static Finder builder(android.support.v4.app.Fragment fragment) {
        return new Finder(fragment.getView());
    }

    public static   Finder builder(View view) {
        return new Finder(view);
    }

    /**
     * find view by id from view
     *
     * @param id
     * @return
     * @return: T
     */

    public  <T extends View> T find(@IdRes int id) {
        Optional.checkNull(hostwindow);
        if (hostwindow instanceof View) {
           return  (T) ((View)hostwindow).findViewById(id);
        } else if (hostwindow instanceof Activity) {
            return  (T) ((Activity)hostwindow).findViewById(id);
        }
        return null;
    }



    /**
     * find view and set click event for it
     *
     * @param id
     * @param listener
     */
    public <T extends View> T find(@IdRes int id, View.OnClickListener listener) {
        T t = find(id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }


    public static  <T extends View> T find(Activity activity ,@IdRes int id) {
        Optional.checkNull(activity);
        return (T) activity.findViewById(id);
    }

    public static  <T extends View> T find(Activity activity ,@IdRes int id,View.OnClickListener listener) {
        T t = find(activity,id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }

    public static  <T extends View> T find(Fragment fragment ,@IdRes int id) {
        Optional.checkNull(fragment);
        return find(fragment.getView(), id);
    }


    public static  <T extends View> T find(Fragment fragment ,@IdRes int id,View.OnClickListener listener) {
        T t = find(fragment,id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }


    public static  <T extends View> T find(android.support.v4.app.Fragment fragment , @IdRes int id) {
        Optional.checkNull(fragment);
        return find(fragment.getView(), id);
    }



    public static  <T extends View> T find(android.support.v4.app.Fragment fragment , @IdRes int id, View.OnClickListener listener) {
        T t = find(fragment,id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }


    public static  <T extends View> T find(View view ,@IdRes int id) {
        Optional.checkNull(view);
        return (T) view.findViewById(id);
    }

    /**
     * inflate xml layout
     *
     * @param context
     * @param resource
     * @return
     */
    public static <T extends View> View inflate(@NonNull Context context, @LayoutRes int resource) {
        return (T) inflate(context, resource, null, false);
    }

    /**
     * inflate xml layout
     *
     * @param context
     * @param resource
     * @param viewGroup
     * @return
     */
    public static <T extends View> View inflate(@NonNull Context context, @NonNull int resource, ViewGroup viewGroup) {
        return (T) inflate(context, resource, viewGroup, false);
    }

    public static <T extends View> View inflate(@NonNull Context context, @NonNull int resource, ViewGroup viewGroup, boolean flag) {
        return (T) LayoutInflater.from(context).inflate(resource, viewGroup, flag);
    }

}
