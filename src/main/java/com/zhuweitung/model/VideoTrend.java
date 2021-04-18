package com.zhuweitung.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 视频动态视频信息类
 * @author zhuweitung
 * @create 2021/4/18 
 */
@NoArgsConstructor
@Data
public class VideoTrend implements Serializable {

    @SerializedName("fansOnlyDesc")
    private String fansOnlyDesc;
    @SerializedName("videoSizeType")
    private Integer videoSizeType;
    /**
     * 视频标题
     */
    @SerializedName("caption")
    private String caption;
    @SerializedName("coverUrl")
    private String coverUrl;
    /**
     * 视频id
     */
    @SerializedName("videoId")
    private String videoId;
    @SerializedName("playDuration")
    private String playDuration;
    @SerializedName("disableThrowBanana")
    private Boolean disableThrowBanana;
    @SerializedName("channel")
    private ChannelDTO channel;
    @SerializedName("shareCount")
    private Integer shareCount;
    @SerializedName("stowCount")
    private Integer stowCount;
    @SerializedName("bananaCount")
    private Integer bananaCount;
    @SerializedName("commentCount")
    private Integer commentCount;
    @SerializedName("viewCount")
    private Integer viewCount;
    @SerializedName("shareUrl")
    private String shareUrl;
    @SerializedName("isFavorite")
    private Boolean isFavorite;
    @SerializedName("picShareUrl")
    private String picShareUrl;
    @SerializedName("createTimeGroup")
    private Integer createTimeGroup;
    @SerializedName("tag")
    private List<TagDTO> tag;
    @SerializedName("tagResourceType")
    private Integer tagResourceType;
    /**
     * 视频类型
     */
    @SerializedName("resourceType")
    private Integer resourceType;
    /**
     * ac号
     */
    @SerializedName("resourceId")
    private Integer resourceId;
    @SerializedName("authorId")
    private Integer authorId;
    @SerializedName("likeCount")
    private Integer likeCount;
    /**
     * 创建时间
     */
    @SerializedName("createTime")
    private Long createTime;
    @SerializedName("time")
    private String time;
    /**
     * 是否投蕉
     */
    @SerializedName("isThrowBanana")
    private Boolean isThrowBanana;
    /**
     * 是否点赞
     */
    @SerializedName("isLike")
    private Boolean isLike;
    @SerializedName("user")
    private UserDTO user;

    @NoArgsConstructor
    @Data
    public static class UserDTO {
        @SerializedName("isUpCollege")
        private Boolean isUpCollege;
        @SerializedName("userName")
        private String userName;
        @SerializedName("userId")
        private Integer userId;
    }

    @NoArgsConstructor
    @Data
    public static class ChannelDTO {
        /**
         * 子频道id
         */
        @SerializedName("parentId")
        private Integer parentId;
        /**
         * 子频道名称
         */
        @SerializedName("parentName")
        private String parentName;
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private Integer id;
    }

    @NoArgsConstructor
    @Data
    public static class TagDTO {
        @SerializedName("tagResourceCount")
        private Integer tagResourceCount;
        @SerializedName("tagId")
        private Integer tagId;
        @SerializedName("tagCountStr")
        private String tagCountStr;
        @SerializedName("tagName")
        private String tagName;
    }



    public VideoBase parse2VideoBase() {
        VideoBase videoBase = new VideoBase();
        videoBase.setAc(this.resourceId);
        videoBase.setTitle(this.caption);
        videoBase.setId(this.videoId);
        videoBase.setType(this.resourceType);
        videoBase.setCreateTime(this.createTime);
        videoBase.setIsThrowBanana(this.isThrowBanana);
        videoBase.setIsLike(this.isLike);
        videoBase.setSubChannelId(this.channel.parentId);
        videoBase.setSubChannelName(this.channel.parentName);
        videoBase.setUpId(this.user.userId);
        videoBase.setUpName(this.user.userName);
        return videoBase;
    }
}
