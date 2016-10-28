package cn.ymex.cocccute.flux.action;


import android.support.annotation.NonNull;

import cn.ymex.cocccute.flux.Params;
import cn.ymex.cute.mode.flux.Action;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 *
 * @author ymexc
 */
public class MessageAction extends Action<Params> {

    public static final String ACTION_SEND_MESSAGE = "message_action_send_message";
    public static final String ACTION_GET_TOP250_MOVIES = "message_action_get_top250_movies";

    public MessageAction(@NonNull String type) {
        super(type);
    }

    public MessageAction(@NonNull String type, @NonNull Params data) {
        super(type, data);
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public static void sendMessage(String message) {
        dispatch(new MessageAction(ACTION_SEND_MESSAGE, Params.bulid().with("message", message)));
    }

    public static void getTop250Movies(int start, int count) {
        dispatch(new MessageAction(ACTION_GET_TOP250_MOVIES, Params.bulid().with("start", start).with("count", count)));
    }

}
