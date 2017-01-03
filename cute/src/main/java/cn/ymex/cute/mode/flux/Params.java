package cn.ymex.cute.mode.flux;

import android.support.v4.util.ArrayMap;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/28
 *
 * @author ymexc
 */
public class Params<T> extends ArrayMap<String,T> {

    public static final String K1 = "key_1";
    public static final String K2 = "key_2";
    public static final String K3 = "key_3";
    public static final String K4 = "key_4";
    public static final String K5 = "key_5";

    private Params() {
        super();
    }

    public static Params bulid() {
        return new Params();
    }




    public String string(String key) {
        return (String) this.get(key);
    }

    public int integer(String key) {
        return (int) this.get(key);
    }

    public boolean bool(String key) {
        return (boolean) this.get(key);
    }


    public Params with(String key, T value) {
        this.put(key, value);
        return this;
    }

    public Params k1(T value) {
        return this.with(K1,value);
    }

    public Params k2(T value) {
        return this.with(K2,value);
    }
    public Params k3(T value) {
        return this.with(K3,value);
    }

    public Params k4(T value) {
        return this.with(K4,value);
    }
    public Params k5(T value) {
        return this.with(K5,value);
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