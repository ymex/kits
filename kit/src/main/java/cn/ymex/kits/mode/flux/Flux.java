package cn.ymex.kits.mode.flux;

import android.support.annotation.NonNull;

import cn.ymex.kits.Optional;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/1/27
 * Flux seted event bus and inistance
 *
 * @author ymex
 */
public final class Flux {
    public static volatile Flux instance;
    private static BusAdapter busAdapter;

    public static Flux instance() {

        Flux tempFlux = instance;
        if (tempFlux == null) {
            synchronized (Flux.class) {
                tempFlux = instance;
                if (tempFlux == null) {
                    tempFlux = new Flux();
                    instance = tempFlux;
                }
            }
        }
        return tempFlux;
    }

    private Flux() {
        super();
    }

    public BusAdapter getBusAdapter() {
        Optional.checkNull(busAdapter, "cute.Flux -> busAdapter is null ! please instance it");
        return busAdapter;
    }

    public void setBusAdapter(@NonNull BusAdapter adapter) {
        if (Optional.isNull(busAdapter)) {
            busAdapter = adapter;
            return;
        }
    }

    public interface BusAdapter {
        /**
         * register event
         * @param target
         */
        void register(@NonNull Object target);

        /**
         * unregister event
         * @param target
         */
        void unregister(@NonNull Object target);

        /**
         * post event
         * @param alert
         */
        void post(@NonNull Object alert);
    }
}
