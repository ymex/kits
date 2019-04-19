package cn.ymex.kits;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/11/18
 * 组件查找工具,方便在activity , fragment 和view上查找控件。
 *
 * @author ymexc
 */
public class ViewExt {

    private Object hostwindow;
    private static String ERRORUSE = "Fragment build must after onCreateView. it's a good idea use in onViewCreated.";

    private ViewExt() {
        super();
    }

    private ViewExt(Object obj) {
        this.hostwindow = obj;
    }

    public static ViewExt build(Activity activity) {
        Empty.checkNull(activity);
        return new ViewExt(activity);
    }

    public static ViewExt build(Fragment fragment) {
        Empty.checkNull(fragment);
        View view = fragment.getView();
        if (view == null) {
            throw new IllegalArgumentException(ERRORUSE);
        }
        return new ViewExt(view);
    }

    public static ViewExt build(android.support.v4.app.Fragment fragment) {
        Empty.checkNull(fragment);
        View view = fragment.getView();
        if (view == null) {
            throw new IllegalArgumentException(ERRORUSE);
        }
        return new ViewExt(view);
    }

    public static ViewExt build(View view) {
        Empty.checkNull(view);
        return new ViewExt(view);
    }

    /**
     * find view by id from view
     *
     * @param id
     * @return
     * @return: T
     */

    public <T extends View> T find(@IdRes int id) {
        Empty.checkNull(hostwindow);
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
        Empty.checkNull(activity);
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
        Empty.checkNull(fragment);
        Empty.checkNull(fragment.getView(), ERRORUSE);
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
        Empty.checkNull(fragment);
        Empty.checkNull(fragment.getView(), ERRORUSE);
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
        Empty.checkNull(view);
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

    public static int resId(Class c, String strId, int defstrid) {
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

    /**
     * Return a resource identifier for the given resource name.
     * Note: use of this function is discouraged.  It is much more
     * efficient to retrieve resources by identifier than by name.
     * <p>
     * int applogoId = getIdentifier(R.mipmap.class,"app_logo")
     *
     * @param name The name of the desired resource.
     * @return int The associated resource identifier.  Returns 0 if no such
     * resource was found.  (0 is not a valid resource ID.)
     */
    public static int getIdentifier(Class typeClass, String name) {
        return resId(typeClass, name, 0);
    }


    /**
     * 底部虚拟导航是否显示
     * @param activity activity
     * @return boolean
     */
    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 底部虚拟导航高度
     * @param activity activity
     * @return hight
     */
    public static int getNavigationBarHeight(Activity activity) {
        if (!isNavigationBarShow(activity)) {
            return 0;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    public static int getSceenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight() + getNavigationBarHeight(activity);
    }
}
