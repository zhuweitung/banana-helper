package com.zhuweitung.api;

/**
 * 其他Api枚举类
 * @author zhuweitung
 * @create 2021/4/20
 */
public enum OtherApi {

    SERVERPUSHV2("Turbo版Server酱", "https://sctapi.ftqq.com/"),
    ServerPushTelegram("TelegramBot", "https://api.telegram.org/bot"),
    PushPlus("PushPlus", "http://www.pushplus.plus/send"),
    ;


    private String name;
    private String url;

    OtherApi(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
