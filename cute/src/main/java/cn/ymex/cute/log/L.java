package cn.ymex.cute.log;

import android.util.Log;

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
public final class L {

    /**
     * 不允许实例化
     */
    private L(){}
    private static  Printer printer;
    private static  boolean LOG ;
    static {
        printer = new LPrinter();
        LOG = true;
    }
    /**
     * 设置自定义 printer
     * @param p
     */
    public static void setPrinter(Printer p){
        printer = p;
    }

    /**
     * 设置日志是否打印
     * @param flag
     */
    public static void setLOG(boolean flag){
        LOG= flag;
    }


    public static void v(Object message){
        if (LOG) {
            printer.log(Log.VERBOSE, message);
        }
    }
    public static void v(String tag, Object message){
        if (LOG) {
            printer.log(Log.VERBOSE, tag, message);
        }
    }

    public static void i(Object message){
        if (LOG) {
            printer.log(Log.INFO, message);
        }
    }
    public static void i(String tag, Object message){
        if (LOG) {
            printer.log(Log.INFO, tag, message);
        }
    }


    public static void d(Object message){
        if (LOG) {
            printer.log(Log.DEBUG, message);
        }
    }
    public static void d(String tag, Object message){
        if (LOG) {
            printer.log(Log.DEBUG, tag, message);
        }
    }


    public static void w(Object message){
        if (LOG) {
            printer.log(Log.WARN, message);
        }
    }
    public static void w(String tag, Object message){
        if (LOG) {
            printer.log(Log.WARN, tag, message);
        }
    }


    public static void e(Object message){
        if (LOG) {
            printer.log(Log.ERROR, message);
        }
    }
    public static void e(String tag, Object message){
        if (LOG) {
            printer.log(Log.ERROR, tag, message);
        }
    }

    /**
     * 打印 带有详细信息的Log
     * @param message
     */
    public static void p(Object message){
        if (LOG){
            new SamplePrinter().log(Log.DEBUG,message);
        }
    }

    /**
     * 打印 带有详细信息的Log
     * @param tag
     * @param message
     */
    public static void p(String tag,Object message){
        if (LOG){
            new SamplePrinter().log(Log.DEBUG,tag,message);
        }
    }

}
