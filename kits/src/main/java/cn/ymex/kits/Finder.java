package cn.ymex.kits;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/18
 * 组件查找工具,方便在activity , fragment 和view上查找控件。
 * @author ymexc
 */
public class Finder {

    private Object hostwindow;
    private static String ERRORUSE = "Fragment build must after onCreateView. it's a good idea use in onViewCreated.";

    private Finder() {
        super();
    }

    private Finder(Object obj) {
        this.hostwindow = obj;
    }

    public static Finder build(Activity activity) {
        Optional.checkNull(activity);
        return new Finder(activity);
    }

    public static Finder build(Fragment fragment) {
        Optional.checkNull(fragment);
        View view = fragment.getView();
        if (view == null) {
            throw  new IllegalArgumentException(ERRORUSE);
        }
        return new Finder(view);
    }

    public static Finder build(android.support.v4.app.Fragment fragment) {
        Optional.checkNull(fragment);
        View view = fragment.getView();
        if (view == null) {
            throw new IllegalArgumentException(ERRORUSE);
        }
        return new Finder(view);
    }

    public static Finder build(View view) {
        Optional.checkNull(view);
        return new Finder(view);
    }

    /**
     * find view by id from view
     *
     * @param id
     * @return
     * @return: T
     */

    public <T extends View> T find(@IdRes int id) {
        Optional.checkNull(hostwindow);
        if (hostwindow instanceof View) {
            return (T) ((View) hostwindow).findViewById(id);
        } else if (hostwindow instanceof Activity) {
            return (T) ((Activity) hostwindow).findViewById(id);
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


    public static <T extends View> T find(Activity activity, @IdRes int id) {
        Optional.checkNull(activity);
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T find(Activity activity, @IdRes int id, View.OnClickListener listener) {
        T t = find(activity, id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }

    public static <T extends View> T find(Fragment fragment, @IdRes int id) {
        Optional.checkNull(fragment);
        Optional.checkNull(fragment.getView(),ERRORUSE);
        return find(fragment.getView(), id);
    }


    public static <T extends View> T find(Fragment fragment, @IdRes int id, View.OnClickListener listener) {
        T t = find(fragment, id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }


    public static <T extends View> T find(android.support.v4.app.Fragment fragment, @IdRes int id) {
        Optional.checkNull(fragment);
        Optional.checkNull(fragment.getView(),ERRORUSE);
        return find(fragment.getView(), id);
    }


    public static <T extends View> T find(android.support.v4.app.Fragment fragment, @IdRes int id, View.OnClickListener listener) {
        T t = find(fragment, id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
        return t;
    }


    public static <T extends View> T find(View view, @IdRes int id) {
        Optional.checkNull(view);
        return (T) view.findViewById(id);
    }

    public static <T extends View> T find(View view, @IdRes int id, View.OnClickListener listener) {
        T t = find(view, id);
        if (t != null) {
            t.setOnClickListener(listener);
        }
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


    /**
     * 获取资源id
     *
     * @param c     类
     * @param strId id
     * @return 实际id
     */

    public static int resId(Class c, String strId,int defstrid) {
        if (c == null || TextUtils.isEmpty(strId)) {
            return defstrid;
        }
        int lastDotIndex = strId.lastIndexOf(".");
        String variableName = strId;
        if (lastDotIndex >= 0) {
            variableName = strId.substring(lastDotIndex + 1, strId.length());
        }
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defstrid;
    }

}
