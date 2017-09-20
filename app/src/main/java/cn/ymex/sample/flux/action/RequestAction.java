package cn.ymex.sample.flux.action;

import android.support.annotation.NonNull;

import cn.ymex.kits.mode.flux.Action;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/28
 * 网络请求动作
 * @author ymexc
 */
public class RequestAction extends Action<String> {
    public static final String START = "new_request_start";
    public static final String SUCCESS = "net_request_result_success";
    public static final String FAILURE = "net_request_result_failure";

    public RequestAction(@NonNull String type, @NonNull String data) {
        super(type, data);
    }

    public static RequestAction SUCCESS() {
        return new RequestAction(SUCCESS, null);
    }

    public static RequestAction FAILURE() {
        return new RequestAction(FAILURE, null);
    }

    public static RequestAction Start() {
        return new RequestAction(START, null);
    }
}
