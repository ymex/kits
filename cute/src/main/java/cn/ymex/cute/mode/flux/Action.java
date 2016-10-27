package cn.ymex.cute.mode.flux;

import android.support.annotation.NonNull;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/01/27
 * 对应用flux Action
 * @author ymex
 */
public class Action<T> {
    /**
     * 事件类型
     */
    private final String type;
    /**
     * 携带数据
     */
    private final T data;

    public Action(@NonNull String type,@NonNull T data) {
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
     * 传递Action到分发器Dispatcher
     * @param action
     */
    public static void dispatch(Action action) {
        Dispatcher.instance().dispatch(action);
    }
}
