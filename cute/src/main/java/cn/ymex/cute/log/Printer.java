package cn.ymex.cute.log;

import android.util.Log;
import java.lang.reflect.Method;

/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p/>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 * @date 15/11/29
 */
public abstract class Printer {
    public static final int ENV_STACK = 7;
    public static final int JSON_INDENT = 4;
    public static final String TAG_E = "cute.E";//打印错误信息tag
    public static String TAG = "cute.L";//默认日志tag
    /**
     * 日志头部内容
     * @return
     */
    public abstract String logHeader();

    /**
     * 日志底部内容
     * @return
     */
    public abstract String logFooter();

    /**
     * 日志内容
     * @return
     */
    public abstract String logContent(Object message);

    /**
     * 打印日志
     * @param leve 日志级别
     * @param ob 日志内容
     */
    public  void log(int leve, Object ob) {
        log(Log.DEBUG,"", ob);
    }

    /**
     * 打印日志
     * @param leve 级别android.utils.LOG 中的级别
     * @param tag
     * @param message
     */
    public void log(int leve, String tag, Object message) {
        boolean throwableFlag = false;
        StringBuilder builder = new StringBuilder(logHeader());
        builder.append(logContent(message));
        builder.append(logFooter());
        if (message instanceof Throwable) {
           throwableFlag = true;
        }
        tag = generateTag(tag);
        switch (leve) {
            case Log.INFO:
                if (throwableFlag){
                    Log.i(tag,builder.toString(),(Exception)message);
                }else {
                    Log.i(tag,builder.toString());

                }
                break;
            case Log.DEBUG:
                if (throwableFlag){
                    Log.d(tag,builder.toString(),(Exception)message);
                }else {
                    Log.d(tag,builder.toString());

                }
                break;
            case Log.ERROR:
                if (throwableFlag){
                    Log.e(tag,builder.toString(),(Exception)message);
                }else {
                    Log.e(tag,builder.toString());

                }
                break;
            case Log.WARN:
                if (throwableFlag){
                    Log.w(tag,builder.toString(),(Exception)message);
                }else {
                    Log.w(tag,builder.toString());

                }
                break;
            case Log.VERBOSE:
                if (throwableFlag){
                    Log.v(tag,builder.toString(),(Exception)message);
                }else {
                    Log.v(tag,builder.toString());

                }
                break;
        }
    }


    /**
     * 生成默认标签
     * @return 如：D/cute.L/MainActivity
     */
    public  String generateTag() {
        return generateTag(TAG);
    }

    /**
     * 生成用户配置标签：D/Tag/MainActivity
     * @param tag
     * @return
     */
    public String generateTag(String tag) {
        if (tag.length()<=0|| tag==null){
            tag = TAG;
        }
        StringBuilder builder = new StringBuilder(tag + "/");
        String name = getLayerStackTrace(ENV_STACK).getClassName();
        int lastIndex = name.lastIndexOf(".");
        name = name.substring(lastIndex + 1);
        int i = name.indexOf("$");
        return builder.append(i == -1 ? name : name.substring(0, i)).toString();
    }

    public  StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    public  StackTraceElement getLayerStackTrace(int layer) {
        return Thread.currentThread().getStackTrace()[layer];
    }

    /**
     * 生成方法详情
     * @param method
     * @return
     */
    public  String methodDetail(Method method) {
        Class typeCls[] = method.getParameterTypes();
        StringBuilder builder = new StringBuilder();
        for (Class clazz : typeCls) {
            builder.append(", ");
            builder.append(clazz.getSimpleName());
        }
        return method.getReturnType().getSimpleName() + " " + method.getName() + "(" + (builder.length() >= 2 ? builder.substring(2) : builder.toString()) + ")";
    }

    /**
     * 判断一个对象是否是数组
     * @param object
     * @return
     */
    public  boolean isArray(Object object){
        return object.getClass().isArray();
    }

    /**
     * 判断数组的维度
     * @param object
     * @return
     */
    public  int dimensionCount(Object object){
        return object.getClass().getName().split("\\[").length-1;
    }

    /**
     * 生成带链接的日志信息
     * @param element
     * @return
     */
    public String generateLinkMessage(StackTraceElement element){
        StringBuilder builder = new StringBuilder();
        builder.append(element.getClassName());
        builder.append(" / ");
        builder.append("(");
        builder.append(element.getFileName());
        builder.append(":");
        builder.append(element.getLineNumber());
        builder.append(")");
        return builder.toString();
    }
}
