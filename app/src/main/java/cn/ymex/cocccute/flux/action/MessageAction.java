package cn.ymex.cocccute.flux.action;


import cn.ymex.cute.mode.flux.Action;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 *
 * @author ymexc
 */
public class MessageAction extends Action<String> {

    public static final String ACTION_SEND_MESSAGE = "message_action_send_message";

    public MessageAction(String type, String data) {
        super(type, data);
    }

    /**
     * 发送消息
     * @param message
     */
    public static void sendMessage(String message) {
        dispatch(new MessageAction(ACTION_SEND_MESSAGE,message));
    }
}
