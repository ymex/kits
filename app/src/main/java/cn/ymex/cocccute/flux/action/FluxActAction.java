package cn.ymex.cocccute.flux.action;


import android.support.annotation.NonNull;

import cn.ymex.kits.mode.flux.Action;
import cn.ymex.kits.mode.flux.Params;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 *
 * @author ymexc
 */
public class FluxActAction extends Action<Params> {

    public static final String ACTION_GET_TOP250_MOVIES = "message_action_get_top250_movies";
    public static final String ACTION_CACEL_REQUEST = "message_action_cacel_request";

    public FluxActAction(@NonNull String type) {
        super(type);
    }

    public FluxActAction(@NonNull String type, @NonNull Params data) {
        super(type, data);
    }



    /**
     * 获取top250请求
     * @param start
     * @param count
     */
    public static void getTop250Movies(int start, int count) {
        dispatch(ACTION_GET_TOP250_MOVIES, Params.bulid().with("start", start).with("count", count));
    }

    /**
     * 取消请求
     */
    public static void cancelHttpRequest() {
        dispatch( ACTION_CACEL_REQUEST);
    }
}
