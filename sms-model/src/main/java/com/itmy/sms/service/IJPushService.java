package com.itmy.sms.service;

import cn.jpush.api.push.model.PushPayload;
import com.itmy.sms.entity.Push;


public interface IJPushService {
    boolean pushAll(Push pushBean);

    boolean pushIos(Push pushBean);

    boolean pushIos(Push pushBean, String... registids);

    boolean pushAndroid(Push pushBean);

    boolean pushAndroid(Push pushBean, String... registids);

    boolean sendPush(PushPayload pushPayload);

}
