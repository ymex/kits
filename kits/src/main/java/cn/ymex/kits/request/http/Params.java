package cn.ymex.kits.request.http;

import java.util.TreeMap;

/**
 * 构造 app  http 请求的参数
 */
public final class Params extends TreeMap<String, String> {
    


    private Params() {
        super();
    }

    /**
     * 预留基础 共用参数 如版本信息，终端类型等
     */
    private static Params baseKeyValue(Params params) {
        return params;
    }


    /**
     * 默认参数构造器
     *
     * @return
     */
    public static Params stream() {
        Params params = new Params();
        return baseKeyValue(params);
    }


    /**
     * 链式构造 参数
     *
     * @param key
     * @param value
     * @return
     */
    public Params with(String key, String value) {
        put(key, value);
        return this;
    }

    /**
     * 链式构造 参数
     *
     * @param key
     * @param value
     * @return
     */
    public Params with(String key, int value) {
        put(key, String.valueOf(value));
        return this;
    }

    /**
     * 构造参数部分
     * @return
     */
    public String urlparam() {
        StringBuilder builder = new StringBuilder("?");
        for (Entry<String, String> entry : this.entrySet()) {
            if (dealStr(entry.getKey()).length() > 0) {
                builder.append(entry.getKey())
                        .append("=")
                        .append(dealStr(entry.getValue())).append("&");
            }
        }

        return builder.substring(0, builder.length() - 1);
    }

    /**
     * 返回json串
     *
     * @return
     */
    public String json() {
        String left = "{";
        String right= "}";
        String colone = ":";
        String quotation = "\"";
        String comma = ",";

        StringBuilder builder = new StringBuilder(left);
        for (Entry<String, String> entry : this.entrySet()) {
            String key = dealStr(entry.getKey());
            String value = dealStr(entry.getValue());
            if (key.length() > 0 && value.length()>0) {
                builder.append(quotation).append(key).append(quotation)
                        .append(colone)
                        .append(quotation).append(value).append(quotation)
                        .append(comma);
            }
        }

        builder.append(right);

        return builder.toString();
    }

    private String dealStr(String text){
        String temp = "";
        if (text != null && text.length() > 0) {
            temp = text;
        }
        return temp;
    }


}
