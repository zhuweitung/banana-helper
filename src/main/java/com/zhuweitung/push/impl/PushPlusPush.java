package com.zhuweitung.push.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhuweitung.api.OtherApi;
import com.zhuweitung.push.AbstractPush;
import com.zhuweitung.push.model.PushMetaInfo;
import lombok.Getter;

/**
 * PushPlus推送
 *
 * @author zhuweitung
 * @create 2022/9/6
 */
public class PushPlusPush extends AbstractPush {

    /**
     * PushPlus 默认TOKEN长度
     */
    public static final int TOKEN_LENGTH = 32;

    @Override
    protected String generatePushUrl(PushMetaInfo metaInfo) {
        return OtherApi.PushPlus.getUrl();
    }

    @Override
    protected boolean checkPushStatus(JsonObject jsonObject) {
        if (null == jsonObject) {
            return false;
        }

        // See https://www.pushplus.plus/doc/guide/api.html
        JsonElement code = jsonObject.get("code");

        if (code == null) {
            return false;
        }

        return code.getAsInt() == 200;
    }

    @Override
    protected String generatePushBody(PushMetaInfo metaInfo, String content) {
        return new Gson().toJson(new PushModel(metaInfo.getToken(), content));
    }

    @Getter
    static class PushModel {
        private final String title = "banana-helper任务简报";
        private final String template = "markdown";
        private final String token;
        private final String content;

        public PushModel(String token, String content) {
            this.token = token;
            this.content = content;
        }
    }
}
