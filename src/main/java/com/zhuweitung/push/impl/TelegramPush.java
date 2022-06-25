package com.zhuweitung.push.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhuweitung.api.OtherApi;
import com.zhuweitung.push.AbstractPush;
import com.zhuweitung.push.model.PushMetaInfo;

/**
 * TG推送
 *
 * @author itning
 * @since 2021/3/22 17:55
 */
public class TelegramPush extends AbstractPush {

    @Override
    protected String generatePushUrl(PushMetaInfo metaInfo) {
        return OtherApi.ServerPushTelegram.getUrl() + metaInfo.getToken() + "/sendMessage";
    }

    @Override
    protected boolean checkPushStatus(JsonObject jsonObject) {
        if (null == jsonObject) {
            return false;
        }

        JsonElement ok = jsonObject.get("ok");
        if (null == ok) {
            return false;
        }

        return "true".equals(ok.getAsString());
    }

    @Override
    protected String generatePushBody(PushMetaInfo metaInfo, String content) {
        return "chat_id=" + metaInfo.getChatId() + "&text=banana-helper任务简报\n" + content;
    }
}
