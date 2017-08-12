package cn.ymex.cuteact.mode.flux;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/1/28
 * store to view action
 * @author ymexc
 */
public class StoreAction<T> {
    private Action action;
    private T result;

    private StoreAction() {
        super();
    }


    public T getResult() {
        return result;
    }

    public Action getAction() {
        return action;
    }

    public StoreAction action(Action action) {
        this.action = action;
        return this;
    }

    public StoreAction result(T result) {
        this.result = result;
        return this;
    }

    public static StoreAction bulid() {
        return new StoreAction();
    }
}
