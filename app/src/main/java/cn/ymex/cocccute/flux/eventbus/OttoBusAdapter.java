package cn.ymex.cocccute.flux.eventbus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import cn.ymex.kits.mode.flux.Flux;


/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 * eventbut adapter for flux
 * @author ymex
 */
public class OttoBusAdapter implements Flux.BusAdapter {
    private static OttoBusAdapter ottoBus;

    public static OttoBusAdapter get() {
        if (ottoBus == null) {
            ottoBus = new OttoBusAdapter();
        }
        return ottoBus;
    }

    private static Bus bus;


    public OttoBusAdapter() {
        if (bus == null) {
            bus = new Bus(ThreadEnforcer.ANY);
        }
    }

    /**
     * 注册
     *
     * @param target
     */
    public void register(Object target) {
        bus.register(target);
    }

    /**
     * 注销
     *
     * @param target
     */
    public void unregister(Object target) {
        bus.unregister(target);
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public void post(Object event) {
        bus.post(event);
    }
}
