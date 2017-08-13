/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex 15/05/01
 */
package cn.ymex.kits.log;


public final class SimplePrinter extends Printer {
    public static String SINGLE_ARROW_RIGHT = ">>>";
    public static String SINGLE_DIVIDER = "─────────────────────────────────────────";

    @Override
    public String logHeader() {
        StackTraceElement element = getLayerStackTrace(ENV_STACK);
        StringBuilder builder = new StringBuilder();
        builder.append(SINGLE_DIVIDER);
        builder.append(SINGLE_ARROW_RIGHT);
        builder.append("\n");
        builder.append(generateLinkMessage(element));
        return builder.toString();
    }

    @Override
    public String logContent(Object message) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append(message==null?"object is null": message.toString());
        return builder.toString();
    }
    @Override
    public String logFooter() {
        return "\n"+SINGLE_DIVIDER;
    }
}
