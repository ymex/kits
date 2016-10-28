package cn.ymex.cocccute.flux;


import android.support.v4.util.ArrayMap;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/28
 *
 * @author ymexc
 */
public class Params<T> extends ArrayMap<String,T>{
    private Params() {
        super();
    }

    public static Params bulid() {
        return new Params();
    }

    public Params with(String key, T value) {
        this.put(key, value);
        return this;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{\n");
        for (Entry<String, T> entry : this.entrySet()) {
            builder.append("    ").append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        builder.append("}");
        return builder.toString();
    }
}
