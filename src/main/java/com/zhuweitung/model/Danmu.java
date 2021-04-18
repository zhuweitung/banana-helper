package com.zhuweitung.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 弹幕信息类
 * @author zhuweitung
 * @create 2021/4/18 
 */
@NoArgsConstructor
@Data
public class Danmu {

    @SerializedName("likeCount")
    private Integer likeCount;
    @SerializedName("roleId")
    private Integer roleId;
    @SerializedName("danmakuId")
    private Integer danmakuId;
    @SerializedName("danmakuType")
    private Integer danmakuType;
    @SerializedName("color")
    private Integer color;
    @SerializedName("createTime")
    private Long createTime;
    @SerializedName("rank")
    private Integer rank;
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("isLike")
    private Boolean isLike;
    @SerializedName("body")
    private String body;
    @SerializedName("mode")
    private Integer mode;
    @SerializedName("position")
    private Integer position;
    @SerializedName("size")
    private Integer size;


}
