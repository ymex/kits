package cn.ymex.cute.log;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


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
 * @date 15/03/29
 */
public  class LPrinter extends Printer {
    private static String CLASS_DETAIL  = "class │ ";
    private static String STACK_DETAIL  = "call  │ ";
    private static String THREAD_DETAIL = "thread│ ";
    private static String ARROW_RIGHT = " ⥤ ";
    public static String SINGLE_ARROW_RIGHT = ">>>";
    public static String SINGLE_DIVIDER = "─────────────────────────────────────────";
    public static String SINGLE_DOTTED_DIVIDER = "─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ";

    private final static String[] types = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};

    @Override
    public String logHeader() {
        StackTraceElement stackTraceElement[] = getStackTrace();
        StackTraceElement preElement = stackTraceElement[ENV_STACK - 1];
        StackTraceElement element = stackTraceElement[ENV_STACK];

        StringBuilder builder = new StringBuilder();
        if (element == null) {
            builder.append(SINGLE_DIVIDER);
            builder.append(SINGLE_DIVIDER);
            builder.append(SINGLE_ARROW_RIGHT);
            builder.append("current stacktrace is  null");
            builder.append(NEXT_LINE);
            return builder.toString().trim();
        }

        builder.append(SINGLE_DIVIDER);
        builder.append(SINGLE_DIVIDER);
        builder.append(SINGLE_ARROW_RIGHT);
        builder.append(NEXT_LINE);

        builder.append(TAB+CLASS_DETAIL);
        builder.append(generateLinkMessage(element));
        builder.append(NEXT_LINE);



        builder.append(TAB+THREAD_DETAIL);
        builder.append("thread name is ");
        builder.append(Thread.currentThread().getName());
        builder.append(" and id=");
        builder.append(Thread.currentThread().getId());
        builder.append(NEXT_LINE);


        builder.append(TAB+STACK_DETAIL);

        try {
            Method methods[] = Class.forName(element.getClassName()).getDeclaredMethods();
            for (Method method : methods) {
                if (element.getMethodName().equals(method.getName())) {
                    builder.append(methodDetail(method));
                    break;
                }
            }

            builder.append(ARROW_RIGHT);
            Method preMethods[] = Class.forName(preElement.getClassName()).getDeclaredMethods();
            for (Method method : preMethods) {
                if (preElement.getMethodName().equals(method.getName())) {
                    builder.append(methodDetail(method));
                    break;
                }
            }
            builder.append(" @line:");
            builder.append(element.getLineNumber());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        builder.append(NEXT_LINE);
        builder.append(SINGLE_DOTTED_DIVIDER+SINGLE_DOTTED_DIVIDER);
        return builder.toString();
    }

    @Override
    public String logFooter() {
        StringBuilder builder = new StringBuilder();
        builder.append(NEXT_LINE);
        builder.append(SINGLE_DOTTED_DIVIDER+SINGLE_DOTTED_DIVIDER);
        return builder.toString();
    }

    @Override
    public String logContent(Object message) {
        String content = new String();
        if (message != null) {
            if (message instanceof Throwable) {
                content = Log.getStackTraceString((Throwable)message);
            } else if (message instanceof String) {
                String me = message.toString();
                if (me.startsWith("{") || me.startsWith("[")) {
                    content = json(me);
                } else if (me.startsWith("<")) {
                    content = xml(me);
                } else {
                    content = me;
                }
            } else if (isArray(message)) {
                content = compatibleTransformFormtDimensionArray(message);
            } else if (message instanceof Collection) {
                content = collection(message);
            } else if (message instanceof Map) {
                content = map(message);
            } else {
                content = object(message);
            }
        } else if (message instanceof File){
            content = ((File)message).getAbsolutePath();
        }else {
            content = object(message);
        }
        return content;
    }


    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public <T> String object(T object) {
        if (object == null) {
            return "Object{object is null}";
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder("\t");
            builder.append(object.getClass().getSimpleName() + "\n\t{\n");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean flag = false;
                for (String type : types) {
                    if (field.getType().getName().equalsIgnoreCase(type)) {
                        flag = true;
                        Object value = null;
                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException e) {
                            value = e;
                        } finally {
                            builder.append(String.format("\t\t%s=%s,\n", field.getName(),
                                    value == null ? "null" : value.toString()));
                            break;
                        }
                    }
                }
                if (!flag) {
                    builder.append(String.format("\t\t%s=%s, ", field.getName(), "Object"));
                }
            }
            return builder.replace(builder.length() - 2, builder.length() - 1, "\n\t}").toString();
        } else {
            return object.toString();
        }
    }


    /**
     * 格式化json
     *
     * @param json
     * @return
     */
    public String json(String json) {
        String text = new String();
        if (TextUtils.isEmpty(json)) {
            text = "Empty/Null json content";
            return text;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                text = jsonObject.toString(JSON_INDENT);
                return text;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                text = jsonArray.toString(JSON_INDENT);
                return text;
            }
        } catch (JSONException e) {
            text = json;
        }
        return text;
    }

    /**
     * 格式化xml
     *
     * @param xml
     * @return
     */
    public String xml(String xml) {
        String text = new String();
        if (TextUtils.isEmpty(xml)) {
            text = ("Empty/Null xml content");
            return text;
        }
        return xml.replace(">", ">\n");
    }

    /**
     * 格式化集合
     *
     * @param coll
     * @return
     */
    private String collection(Object coll) {

        Collection collection = (Collection) coll;
        String msg = "%s size = %d [\n";
        msg = String.format(msg, coll.getClass().getName(), collection.size());
        if (!collection.isEmpty()) {
            Iterator<Object> iterator = collection.iterator();
            int flag = 0;
            while (iterator.hasNext()) {
                String itemString = "[%d]:%s%s";
                Object item = iterator.next();
                msg += String.format(itemString, flag, object(item),
                        flag++ < collection.size() - 1 ? ",\n" : "");
            }
        }
        return msg + "]";
    }

    /**
     * 格式化map
     *
     * @param object
     * @return
     */
    private String map(Object object) {

        String msg = object.getClass().getName() + "\n{\n";
        Map<Object, Object> map = (Map<Object, Object>) object;
        Set<Object> keys = map.keySet();
        for (Object key : keys) {
            String itemString = "[%s ⥤ %s]\n";
            Object value = map.get(key);
            msg += String.format(itemString, object(key),
                    object(value));
        }
        return msg + "}";
    }
    private  String formt1DimensionArray(Object[] objects) {
        return  formt1DimensionArray(objects,false);
    }
    /**
     * 格式化一维数组
     *
     * @param objects
     * @param flag 是否换行。
     * @return
     */
    private  String formt1DimensionArray(Object[] objects,boolean flag) {
        String className = objects.getClass().getSimpleName();
        int size = objects.length;
        StringBuilder builder = new StringBuilder(className+".length = "+size+"\n{");
        for (int i = 0; i < objects.length; i++) {
            if (flag){
                builder.append("\n["+i+"]");
            }
            builder.append(object(objects[i])).append(",");
        }
        builder = new StringBuilder(removeLastChar(builder.toString()));
        if (flag){
            builder.append("\n}");
        }else {
            builder.append("}");
        }

        return builder.toString();
    }

    private  String formt2DimensionArray(Object[][] objects){
        return  formt2DimensionArray(objects,false);
    }
    /**
     * 格式化二维数组
     *
     * @param objects
     * @param flag
     * @return
     */
    private  String formt2DimensionArray(Object[][] objects,boolean flag) {
        String className = objects.getClass().getSimpleName();
        int xsize =objects.length;
        int ysize =objects[0].length;
        StringBuilder oneBuilder = new StringBuilder(className+".length = ["+xsize+"]["+ysize+"]"+"\n{");
        for (int i = 0; i < objects.length; i++) {

            StringBuilder toBuilder = new StringBuilder();
            if (i == 0) {
                toBuilder.append("{");
            } else {
                toBuilder.append(" {");
            }
            for (int j = 0; j < objects[i].length; j++) {
                if (flag){
                    toBuilder.append("\n["+i+"]["+j+"]");
                }
                toBuilder.append(object(objects[i][j]));
                toBuilder.append(",");
            }
            toBuilder = new StringBuilder(removeLastChar(toBuilder.toString()));
            toBuilder.append("},\n");
            oneBuilder.append(toBuilder);
        }
        oneBuilder.append("}");
        return replaseLast(oneBuilder.toString(), ",\n", "");
    }

    /**
     * 获得数组类型
     *
     * @param t
     * @return
     */
    public  <T> String arrayType(T t) {
        String name = t.getClass().getSimpleName();
        return name.split("\\[")[0];
    }
    /**
     * 适配 数组类型
     *
     * @param object
     * @return
     */
    private  String compatibleTransformFormtDimensionArray(Object object) {
        int count = dimensionCount(object);
        if (count == 1) {// 匹配一维数组--开始
            if (Arrays.asList(types).contains(arrayType(object))) {
                for (String type : types) {
                    if (type.equals("int")) {
                        int[] srcObs = (int[]) object;
                        Integer[] obs = new Integer[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Integer(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("boolean")) {
                        boolean[] srcObs = (boolean[]) object;
                        Boolean[] obs = new Boolean[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Boolean(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("char")) {
                        char[] srcObs = (char[]) object;
                        Character[] obs = new Character[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Character(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("float")) {
                        float[] srcObs = (float[]) object;
                        Float[] obs = new Float[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Float(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("double")) {
                        double[] srcObs = (double[]) object;
                        Double[] obs = new Double[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Double(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("long")) {
                        long[] srcObs = (long[]) object;
                        Long[] obs = new Long[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Long(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("short")) {
                        short[] srcObs = (short[]) object;
                        Short[] obs = new Short[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Short(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    } else if (type.equals("byte")) {
                        byte[] srcObs = (byte[]) object;
                        Byte[] obs = new Byte[srcObs.length];
                        for (int i = 0; i < obs.length; i++) {
                            obs[i] = new Byte(srcObs[i]);
                        }
                        return formt1DimensionArray(obs);
                    }
                }
            } else {
                return formt1DimensionArray((Object[]) object,true);
            }

            //匹配一维数组--结束
        } else if (count == 2) {//匹配二维数组--开始
            if (Arrays.asList(types).contains(arrayType(object))) {
                for (String type : types) {
                    if (type.equals("int")) {
                        int[][] srcObs = (int[][]) object;
                        Integer[][] obs = new Integer[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Integer(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("boolean")) {
                        boolean[][] srcObs = (boolean[][]) object;
                        Boolean[][] obs = new Boolean[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Boolean(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("char")) {
                        char[][] srcObs = (char[][]) object;
                        Character[][] obs = new Character[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Character(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("float")) {
                        float[][] srcObs = (float[][]) object;
                        Float[][] obs = new Float[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Float(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("double")) {
                        double[][] srcObs = (double[][]) object;
                        Double[][] obs = new Double[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Double(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("long")) {
                        long[][] srcObs = (long[][]) object;
                        Long[][] obs = new Long[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Long(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("short")) {
                        short[][] srcObs = (short[][]) object;
                        Short[][] obs = new Short[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Short(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs);
                    } else if (type.equals("byte")) {
                        byte[][] srcObs = (byte[][]) object;
                        Byte[][] obs = new Byte[srcObs.length][srcObs[0].length];
                        for (int i = 0; i < obs.length; i++) {
                            for (int j = 0; j < obs[i].length; j++) {
                                obs[i][j] = new Byte(srcObs[i][j]);

                            }
                        }
                        return formt2DimensionArray(obs,true);
                    }
                }
            } else {
                return formt2DimensionArray((Object[][]) object);
            }
        }//匹配二维数组--结束
        return object.toString();
    }
    /**
     * 替换最后一个older字串为指定新字串newer
     *
     * @param text
     * @param older
     * @param newer
     * @return
     */
    private static String replaseLast(String text, String older, String newer) {
        int last = text.lastIndexOf(older);
        StringBuilder builder = new StringBuilder(text.substring(0, last));
        builder.append(newer);
        builder.append(text.substring(last + older.length(), text.length()));
        return builder.toString();
    }

    /**
     * 移除最后的字符
     *
     * @param text
     * @return
     */
    private static String removeLastChar(String text) {
        return text.substring(0, text.length() - 1);
    }


    /**
     * 移除最后的指定字串
     *
     * @param text
     * @param str
     * @return
     */
    private static String removeLastStr(String text, String str) {
        return text.substring(0, text.lastIndexOf(str));
    }
}
