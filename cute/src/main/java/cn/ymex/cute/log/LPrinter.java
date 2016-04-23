package cn.ymex.cute.log;

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
 * @date 15/12/7
 */
public final class LPrinter extends Printer {
    public static String ARROW_RIGHT = ">>>";
    public static String DIVIDER_LINE  = "────────────────────────────────────────────────────────";
    @Override
    public String logHeader() {
        StackTraceElement element = getLayerStackTrace(ENV_STACK);
        StringBuilder builder = new StringBuilder();
        builder.append(DIVIDER_LINE);
        builder.append(ARROW_RIGHT);
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
        return "\n"+DIVIDER_LINE;
    }
}
