package com.zhuweitung.push.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhuweitung.api.OtherApi;
import com.zhuweitung.push.AbstractPush;
import com.zhuweitung.push.model.PushMetaInfo;
import lombok.extern.log4j.Log4j2;

/**
 * Turbo版本server酱推送
 *
 * @author itning
 * @since 2021/3/22 17:14
 */
@Log4j2
public class ServerChanTurboPush extends AbstractPush {

    @Override
    protected String generatePushUrl(PushMetaInfo metaInfo) {
        return OtherApi.SERVERPUSHV2.getUrl() + metaInfo.getToken() + ".send";
    }

    @Override
    protected boolean checkPushStatus(JsonObject jsonObject) {
        if (null == jsonObject) {
            return false;
        }
        // {"code":0,"message":"","data":{"pushid":"XXX","readkey":"XXX","error":"SUCCESS","errno":0}}
        JsonElement code = jsonObject.get("code");

        if (null == code) {
            return false;
        }

        // FIX #380
        switch (code.getAsInt()) {
            case 0:
                return true;
            case 40001:
                log.info("超过当天的发送次数限制[10]，请稍后再试");
                return true;
            default:
                return code.getAsInt() == 0;
        }
    }

    @Override
    protected String generatePushBody(PushMetaInfo metaInfo, String content) {
        return "title=banana-helper任务简报&desp=" + content;
    }
}
