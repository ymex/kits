package cn.ymex.cute.mode.flux;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/28
 *
 * @author ymexc
 */
public class StoreAlter<T> {
    private Action action;
    private T result;

    private StoreAlter() {
        super();
    }


    public T getResult() {
        return result;
    }

    public Action getAction() {
        return action;
    }

    public StoreAlter action(Action action) {
        this.action = action;
        return this;
    }

    public StoreAlter result(T result) {
        this.result = result;
        return this;
    }

    public static StoreAlter bulid() {
        return new StoreAlter();
    }
}
