package com.zhuweitung.model;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;

/**
 * 视频信息基础类
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Data
public class VideoBase implements Serializable {

    /**
     * ac号
     */
    private Integer ac;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频id
     */
    private String id;
    /**
     * 视频类型
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 是否投蕉
     */
    private Boolean isThrowBanana;
    /**
     * 是否点赞
     */
    private Boolean isLike;
    /**
     * 子频道id
     */
    private Integer subChannelId;
    /**
     * 子频道名称
     */
    private String subChannelName;
    /**
     * up id
     */
    private Integer upId;
    /**
     * up昵称
     */
    private String upName;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
