package cn.ymex.cute.mode.flux;

import android.support.annotation.NonNull;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/01/27
 * Flux Store
 *
 * @author ymex
 */
public abstract class Store {

    protected Store() {
    }

    public void register(@NonNull final Object view) {
        Dispatcher.instance().register(this);
        Flux.instance().getBusAdapter().register(view);
    }

    public void unregister(@NonNull final Object view) {
        Dispatcher.instance().unregister(this);
        Flux.instance().getBusAdapter().unregister(view);
    }

    /**
     * @param action
     * @return true is auto send event
     * else you must call emitStoreChange(Action action) function
     */
    public abstract boolean onStoreAction(Action action);

    public void onAction(Action action) {
        if (onStoreAction(action)) {
            emitStoreChange(action);
        }
    }

    protected void emitStoreChange(String type) {
        emitStoreChange(StoreAction.bulid().action(Action.bulid(type)));
    }

    /**
     * @param type
     * @param result
     */
    protected void emitStoreChange(String type, Object result) {
        emitStoreChange(StoreAction.bulid().action(Action.bulid(type)).result(result));
    }

    /**
     *
     * @param type
     * @param data
     * @param result
     */
    protected void emitStoreChange(String type, Object data, Object result) {
        emitStoreChange(StoreAction.bulid().action(Action.bulid(type, data)).result(result));
    }

    protected void emitStoreChange(Action action) {
        emitStoreChange(StoreAction.bulid().action(action));
    }

    /**
     * @param action
     * @param result
     */
    protected void emitStoreChange(Action action, Object result) {
        emitStoreChange(StoreAction.bulid().action(action).result(result));
    }

    /**
     * @param action
     */
    protected void emitStoreChange(StoreAction action) {
        Flux.instance().getBusAdapter().post(action);
    }
}
