package cn.ymex.cute.mode.flux;

import android.support.annotation.NonNull;

import cn.ymex.cute.kits.Optional;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/01/27
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
        Optional.checkNull(busAdapter, "Flux -> busAdapter is null ! please instance it");
        return busAdapter;
    }

    public void setBusAdapter(@NonNull BusAdapter adapter) {
        if (Optional.isNull(busAdapter)) {
            busAdapter = adapter;
            return;
        }
    }

    public interface BusAdapter {
        void register(@NonNull Object target);

        void unregister(@NonNull Object target);

        void post(@NonNull Object event);
    }
}
