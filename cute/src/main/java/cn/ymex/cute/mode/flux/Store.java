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
     */
    public abstract boolean onStoreAction(Action action);

    public void onAction(Action action) {
        if (onStoreAction(action)) {
            emitStoreChange(action);
        }
    }

    protected void emitStoreChange(Action action) {
        emitStoreChange(StoreAlter.bulid().action(action));
    }
    protected void emitStoreChange(StoreAlter alter) {
        Flux.instance().getBusAdapter().post(alter);
    }


}
