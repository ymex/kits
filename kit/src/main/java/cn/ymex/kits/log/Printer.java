/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex  15/03/29
 */
package cn.ymex.kits.log;

import android.util.Log;

import java.lang.reflect.Method;


public abstract class Printer {
    public static final int ENV_STACK = 7;
    public static final int JSON_INDENT = 4;
    public static final int MAX_CHARS= 4000;
    public static final String TAG_E = "cute.E";//打印错误信息tag
    public static String TAG = "cute.L";//默认日志tag

    public static final String NEXT_LINE = "\n";
    public static final String TAB = "\t";

    /**
     * 日志头部内容
     *
     * @return
     */
    public abstract String logHeader();

    /**
     * 日志底部内容
     *
     * @return
     */
    public abstract String logFooter();

    /**
     * 日志内容
     *
     * @return
     */
    public abstract String logContent(Object message);

    /**
     * 打印日志
     *
     * @param leve 日志级别
     * @param ob   日志内容
     */
    public void log(int leve, Object ob) {
        log(leve, "", ob);
    }

    /**
     * 打印日志
     *
     * @param leve    级别android.utils.LOG 中的级别
     * @param tag
     * @param message
     */
    public void log(int leve, String tag, Object message) {

        StringBuilder builder = new StringBuilder(logHeader().trim());
        builder.append(NEXT_LINE);
        builder.append(logContent(message).trim());
        builder.append(NEXT_LINE);
        builder.append(logFooter().trim());
        builder.append(NEXT_LINE);
        int logLength = builder.toString().length();
        if (logLength > MAX_CHARS) {
            _log(leve,tag,logHeader().toString());
            String logContent = logContent(message).trim().toString();
            int len = logContent.length();
            int count = logContent.length()/MAX_CHARS;
            for (int i = 0; i<= count; i++){
                int start = i*MAX_CHARS;
                int end = start+MAX_CHARS;
                if (i==count){
                    _log(leve, tag, logContent.substring(start, len));
                }else {
                    _log(leve, tag, logContent.substring(start, end));
                }

            }
            _log(leve,tag,logFooter().toString().trim());
            return;
        }
        _log(leve, tag, builder.toString());
    }

    private void _log(int leve, String tag, String log) {

        tag = generateTag(tag);
        switch (leve) {
            case Log.INFO:
                Log.i(tag, log);
                break;
            case Log.DEBUG:
                Log.d(tag, log);
                break;
            case Log.ERROR:
                Log.e(tag, log);
                break;
            case Log.WARN:
                Log.w(tag, log);
                break;
            case Log.VERBOSE:
                Log.v(tag, log);
                break;
        }
    }

    /**
     * 生成默认标签
     *
     * @return 如：D/cute.L/MainActivity
     */
    public String generateTag() {
        return generateTag(TAG);
    }

    /**
     * 生成用户配置标签：D/Tag/MainActivity
     *
     * @param tag
     * @return
     */
    public String generateTag(String tag) {
        if (tag.length() <= 0 || tag == null) {
            tag = TAG;
        }
        StringBuilder builder = new StringBuilder(tag + "/");
        String name = getLayerStackTrace(ENV_STACK).getClassName();
        int lastIndex = name.lastIndexOf(".");
        name = name.substring(lastIndex + 1);
        int i = name.indexOf("$");
        return builder.append(i == -1 ? name : name.substring(0, i)).toString();
    }

    public StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    public StackTraceElement getLayerStackTrace(int layer) {
        return Thread.currentThread().getStackTrace()[layer];
    }

    /**
     * 生成方法详情
     *
     * @param method
     * @return
     */
    public String methodDetail(Method method) {
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
     *
     * @param object
     * @return
     */
    public boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    /**
     * 判断数组的维度
     *
     * @param object
     * @return
     */
    public int dimensionCount(Object object) {
        return object.getClass().getName().split("\\[").length - 1;
    }

    /**
     * 生成带链接的日志信息
     *
     * @param element
     * @return
     */
    public String generateLinkMessage(StackTraceElement element) {
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
