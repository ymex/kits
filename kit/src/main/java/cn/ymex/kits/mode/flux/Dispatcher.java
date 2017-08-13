package cn.ymex.kits.mode.flux;

import java.util.Vector;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/1/27
 * Flux Dispatcher
 * @author ymex
 */
public class Dispatcher {
    private static volatile Dispatcher instance;
    private final Vector<Store> stores = new Vector<>();

    public static Dispatcher instance() {
        Dispatcher dispatcher = instance;
        if (dispatcher == null) {
            synchronized (Dispatcher.class) {
                dispatcher = instance;
                if (dispatcher == null) {
                    dispatcher = new Dispatcher();
                    instance = dispatcher;
                }
            }
        }
        return dispatcher;
    }

    private Dispatcher() {
    }

    public void register(final Store store) {
        if (!stores.contains(store)) {
            stores.add(store);
        }
    }

    public void register(final Store... stores) {
        for (Store store : stores) {
            register(store);
        }
    }

    public void unregister(final Store store) {
        stores.remove(store);
    }

    public void unregister(final Store... stores) {
        for (Store store : stores) {
            unregister(store);
        }
    }

    public void dispatch(Action action) {
        post(action);
    }

    private void post(final Action action) {
        for (Store store : stores) {
            if (store != null) {
                store.onAction(action);
            }
        }
    }
}
