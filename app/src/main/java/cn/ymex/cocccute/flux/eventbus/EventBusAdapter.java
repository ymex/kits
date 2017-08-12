package cn.ymex.cocccute.flux.eventbus;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import cn.ymex.cuteact.mode.flux.Flux;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 * eventbut adapter for flux
 * @author ymex
 */
public class EventBusAdapter implements Flux.BusAdapter {


    private static EventBusAdapter eventBusAdapter;

    public static EventBusAdapter get() {
        if (eventBusAdapter == null) {
            eventBusAdapter = new EventBusAdapter();
        }
        return eventBusAdapter;
    }


    @Override
    public void register(@NonNull Object target) {
        EventBus.getDefault().register(target);
    }

    @Override
    public void unregister(@NonNull Object target) {
        EventBus.getDefault().unregister(target);
    }

    @Override
    public void post(@NonNull Object event) {
        EventBus.getDefault().post(event);
    }
}
