package cn.ymex.cocccute.flux;


import cn.ymex.cocccute.flux.model.Message;
import cn.ymex.cute.kits.Optional;
import cn.ymex.cute.mode.flux.Action;
import cn.ymex.cute.mode.flux.Store;

/**
 * Copyright (c) ymexc(www.ymex.cn)
 * Email:ymex@foxmail.com
 * date 2016/10/27
 *
 * @author ymexc
 */
public class MessageStore extends Store {

    private Message message;

    public Message getMessage() {
        this.message = Optional.isNull(this.message)?new Message():this.message;
        return message;
    }


    @Override
    public boolean onStoreAction(Action action) {
        switch (action.getType()) {
            case MessageAction.ACTION_SEND_MESSAGE:
                getMessage().setContent(action.getData().toString());
            break;
        }
       return true;
    }
}
