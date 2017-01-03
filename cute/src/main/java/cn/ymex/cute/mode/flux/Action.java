package cn.ymex.cute.mode.flux;

import android.support.annotation.NonNull;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/1/27
 * <p>
 * Flux Action
 *
 * @author ymex
 */
public class Action<T> {
    private final String type;
    private final T data;

    public Action(@NonNull String type) {
        this(type, null);
    }

    public Action(@NonNull String type, @NonNull T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }


    /**
     * Send Action to Dispatcher
     *
     * @param action
     */
    public static void dispatch(Action action) {
        Dispatcher.instance().dispatch(action);
    }

    /**
     * Send Action to Dispatcher
     *
     * @param type
     */
    public static void dispatch(String type) {
        Dispatcher.instance().dispatch(Action.bulid(type));
    }

    /**
     * Send Action to Dispatcher
     *
     * @param type
     * @param data
     */
    public static void dispatch(String type, Object data) {
        Dispatcher.instance().dispatch(Action.bulid(type, data));
    }

    public static Action bulid(String type) {
        return new Action(type);
    }

    public static Action bulid(String type, Object data) {
        return new Action(type, data);
    }
}
