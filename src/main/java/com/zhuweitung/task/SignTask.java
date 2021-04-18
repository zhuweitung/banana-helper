package com.zhuweitung.task;

import com.google.gson.JsonObject;
import com.zhuweitung.api.AcFunApi;
import com.zhuweitung.signin.Acer;
import com.zhuweitung.util.HttpUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 每日签到任务
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
public class SignTask extends Task {

    @Override
    public void run() {
        if (Acer.getInstance().getSignIn()) {
            //已签到
            log.info("今日已签到");
        } else {
            JsonObject responseJson = HttpUtil.doPost(AcFunApi.DAILY_SIGN.getUrl());
            int responseCode = responseJson.get("result").getAsInt();
            if (responseCode == 0) {
                String message = responseJson.get("msg").getAsString();
                log.info("每日签到成功：{}", message);
            } else {
                String message = responseJson.get("error_msg").getAsString();
                log.debug("每日签到失败：{}", message);
            }
        }
    }

    @Override
    public String getName() {
        return "每日签到任务";
    }
}
