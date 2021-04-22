package com.zhuweitung.task;

import com.google.gson.JsonObject;
import com.zhuweitung.api.AcFunApi;
import com.zhuweitung.config.Config;
import com.zhuweitung.util.HttpUtil;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * 分享任务
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
public class ShareTask extends AbstractTask {

    @Override
    public void run() {
        if (Config.getInstance().getIsShare() == 1) {
            Map<String, String> params = new HashMap<>();
            params.put("taskType", "1");
            params.put("market", "tencent");
            params.put("product", "ACFUN_APP");
            params.put("appMode", "0");
            JsonObject responseJson = HttpUtil.doGet(AcFunApi.VIDEO_SHARE.getUrl(), params);
            int responseCode = responseJson.get("result").getAsInt();
            if (responseCode == 0) {
                log.info("分享成功");
            } else {
                log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.VIDEO_SHARE.getName(), responseJson);
            }
        } else {
            log.info("不需要分享，跳过任务");
        }
    }

    @Override
    public String getName() {
        return "分享任务";
    }
}
