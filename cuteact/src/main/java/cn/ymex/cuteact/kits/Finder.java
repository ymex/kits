package cn.ymex.cuteact.kits;

import android.app.Activity;
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
    private Object hostView;

    private Finder() {
    }

    public static Finder build(@NonNull View view) {
        return new Finder(view);
    }

    public static Finder build(@NonNull Activity act) {
        return new Finder(act);
    }

    public Finder(Object obj) {
        if (obj instanceof Activity || obj instanceof View) {
            this.hostView = obj;
        } else {
            throw new RuntimeException("Not allow type for Finder");
        }
    }

    public Finder make(@NonNull View view, @IdRes int id) {
        return make(view,id,null);
    }

    public Finder make(@NonNull View view,@IdRes int id , View.OnClickListener onClickListener) {
        if (hostView instanceof Activity) {
            view = find((Activity) hostView, id);
        } else if (hostView instanceof View){
            view = find((View)hostView,id);
        }
        if (onClickListener != null && view != null) {
            view.setOnClickListener(onClickListener);
        }
        return this;
    }
    /**
     * find view by id from view
     *
     * @param view
     * @param id
     * @return
     * @return: T
     */

    public static <T extends View> T find(@NonNull View view, @IdRes int id) {
        Optional.checkNull(view);
        return (T) view.findViewById(id);
    }

    /**
     * find view by id from activity
     *
     * @param act
     * @param id
     * @return
     * @return: T
     */
    public static <T extends View> T find(@NonNull Activity act, @IdRes int id) {
        Optional.checkNull(act);
        return (T) act.findViewById(id);
    }

    /**
     * find view and set click event for it
     *
     * @param view
     * @param id
     * @param listener
     */
    public static <T extends View> T find(@NonNull View view, @IdRes int id, View.OnClickListener listener) {
        T t = find(view, id);
        t.setOnClickListener(listener);
        return t;
    }

    /**
     * find view and set click event for it
     *
     * @param act
     * @param id
     * @param listener
     */
    public static <T extends View> T find(@NonNull Activity act, @IdRes int id, View.OnClickListener listener) {
        T t = find(act, id);
        t.setOnClickListener(listener);
        return t;
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
