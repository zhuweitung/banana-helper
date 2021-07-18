package com.zhuweitung.api;

/**
 * AcFun Api枚举类
 * @author zhuweitung
 * @create 2021/4/18 
 */
public enum AcFunApi {

    /**
     * 获取TOKEN
     */
    GET_TOKEN("获取TOKEN", "https://id.app.acfun.cn/rest/web/token/get"),
    /**
     * 获取账号信息
     */
    GET_PERSONINFO("获取账号信息", "https://www.acfun.cn/rest/pc-direct/user/personalInfo"),
    /**
     * 获取关注up列表
     */
    GET_FOLLOWUPLIST("获取关注up列表", "https://www.acfun.cn/rest/pc-direct/relation/getFollows"),
    /**
     * 获取关注up视频动态
     */
    GET_FOLLOWUPVIDEOTREND("获取关注up视频动态", "https://www.acfun.cn/rest/pc-direct/feed/webPush"),
    /**
     * 获取香蕉榜
     */
    GET_BANANALIST("获取香蕉榜", "https://www.acfun.cn/rest/pc-direct/rank/channel"),
    /**
     * 每日签到
     */
    DAILY_SIGN("每日签到", "https://www.acfun.cn/rest/pc-direct/user/signIn"),

    /**
     * 视频发送弹幕
     */
    VIDEO_SENDDANMU("视频发送弹幕", "https://www.acfun.cn/rest/pc-direct/new-danmaku/add"),
    /**
     * 视频投蕉
     */
    VIDEO_THROWBANANA("视频投蕉", "https://www.acfun.cn/rest/pc-direct/banana/throwBanana"),
    /**
     * 视频点赞
     */
    VIDEO_LIKE("视频点赞", "https://kuaishouzt.com/rest/zt/interact/add"),
    /**
     * 视频取消点赞
     */
    VIDEO_UNLIKE("视频取消点赞", "https://kuaishouzt.com/rest/zt/interact/delete"),
    /**
     * 视频分享
     */
    VIDEO_SHARE("视频分享", "https://api-ipv6.acfunchina.com/rest/app/task/reportTaskAction"),
    /**
     * 获取视频弹幕列表
     */
    GET_VIDEODANMULIST("获取视频弹幕列表", "https://www.acfun.cn/rest/pc-direct/new-danmaku/list"),
    ;

    private String name;
    private String url;

    AcFunApi(String name, String url) {
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
