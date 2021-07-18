package com.zhuweitung.config;

import com.google.gson.Gson;
import com.zhuweitung.util.HttpUtil;
import com.zhuweitung.util.ConfigLoadUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 配置类
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
@Data
public class Config {

    private static Config CONFIG = new Config();

    /**
     * 每日投蕉数
     * 每个视频5蕉，不足5蕉ALL IN
     */
    private int throwBananaNum;
    /**
     * 投蕉并点赞
     */
    private int throwAndLike;
    /**
     * 投蕉并发送弹幕
     */
    private int throwAndDanmu;
    /**
     * 是否点赞 [0,1]
     */
    private int isLike;
    /**
     * 是否发送弹幕 [0,1]
     */
    private int isDanmu;
    /**
     * 弹幕优先级 [0,1]
     * 0：从视频弹幕池中取频率最高的发送，1：从自己设置的弹幕池中随机取弹幕
     */
    private int danmuPriority;
    /**
     * 弹幕池
     * 弹幕优先级为1才会生效
     */
    private List<String> danmuPool;
    /**
     * 是否分享
     */
    private int isShare;
    /**
     * 投蕉优先级 [0,1]
     * 0：优先给每日香蕉榜投蕉，1：优先给关注的up投蕉
     */
    private int bananaPriority;
    /**
     * 关注up的投蕉优先级
     */
    private List<Integer> followUpPriority;
    /**
     * UA标识
     */
    private String userAgent;
    /**
     * 跳过投蕉点赞弹幕up列表
     */
    private List<Integer> skipUpList;


    private Config() {
    }

    public static Config getInstance() {
        return CONFIG;
    }

    /**
     * @description 配置初始化
     * @param
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public void init() {
        String configJson;
        String customConfig = ConfigLoadUtil.loadCustomConfig();
        if (StringUtils.isNotBlank(customConfig)) {
            configJson = customConfig;
        } else {
            configJson = ConfigLoadUtil.loadProjectConfig();
        }

        Config.CONFIG = new Gson().fromJson(configJson, Config.class);
//        HttpUtil.setUserAgent(Config.getInstance().getUserAgent());
        log.info(Config.getInstance().toString());
    }

    @Override
    public String toString() {
        return "配置信息 {" +
                "每日投蕉数为：" + throwBananaNum +
                "；投蕉并点赞：" + (throwAndLike == 1 ? "是" : "否") +
                "；投蕉并发送弹幕：" + (throwAndDanmu == 1 ? "是" : "否") +
                "；是否点赞：" + (isLike == 1 ? "是" : "否") +
                "；是否发送弹幕：" + (isDanmu == 1 ? "是" : "否") +
                "；弹幕优先级为：" + (danmuPriority == 0 ? "视频弹幕池频率最高" : "自定义弹幕池随机") +
                "；弹幕池为：" + danmuPool.toString() +
                "；是否分享：" + (isShare == 1 ? "是" : "否") +
                "；投蕉策略为：" + (bananaPriority == 0 ? "每日香蕉榜优先" : "关注up优先") +
                "；关注up的投蕉优先级为：" + followUpPriority.toString() +
                "；跳过投蕉点赞弹幕up列表为：" + skipUpList.toString() +
                "}";
    }
}
