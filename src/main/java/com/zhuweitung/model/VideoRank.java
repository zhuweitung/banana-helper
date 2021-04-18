package com.zhuweitung.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 香蕉榜视频信息类
 * @author zhuweitung
 * @create 2021/4/18 
 */
@NoArgsConstructor
@Data
public class VideoRank implements Serializable {


    @SerializedName("groupId")
    private String groupId;
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("dougaId")
    private String dougaId;
    @SerializedName("channelName")
    private String channelName;
    /**
     * ac号
     */
    @SerializedName("contentId")
    private Integer contentId;
    @SerializedName("fansCount")
    private Integer fansCount;
    @SerializedName("isFollowing")
    private Boolean isFollowing;
    @SerializedName("videoCover")
    private String videoCover;
    @SerializedName("channelId")
    private Integer channelId;
    @SerializedName("userImg")
    private String userImg;
    @SerializedName("contributeTime")
    private Long contributeTime;
    @SerializedName("userName")
    private String userName;
    /**
     * 视频标题
     */
    @SerializedName("contentTitle")
    private String contentTitle;
    @SerializedName("contentDesc")
    private String contentDesc;
    @SerializedName("userSignature")
    private String userSignature;
    @SerializedName("contributionCount")
    private Integer contributionCount;
    @SerializedName("danmuCount")
    private Integer danmuCount;
    /**
     * 视频类型
     */
    @SerializedName("contentType")
    private Integer contentType;
    @SerializedName("duration")
    private Integer duration;
    @SerializedName("commentCount")
    private Integer commentCount;
    @SerializedName("durationMillis")
    private Integer durationMillis;
    @SerializedName("bananaCount")
    private Integer bananaCount;
    @SerializedName("videoList")
    private List<VideoListDTO> videoList;
    @SerializedName("danmakuCount")
    private Integer danmakuCount;
    @SerializedName("fansOnlyDesc")
    private String fansOnlyDesc;
    @SerializedName("shareCount")
    private Integer shareCount;
    @SerializedName("originalDeclare")
    private Integer originalDeclare;
    @SerializedName("stowCount")
    private Integer stowCount;
    @SerializedName("giftPeachCount")
    private Integer giftPeachCount;
    @SerializedName("commentCountRealValue")
    private Integer commentCountRealValue;
    @SerializedName("shareUrl")
    private String shareUrl;
    @SerializedName("viewCount")
    private Integer viewCount;
    @SerializedName("coverUrl")
    private String coverUrl;
    @SerializedName("likeCount")
    private Integer likeCount;
    @SerializedName("title")
    private String title;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("hasHotComment")
    private Boolean hasHotComment;
    @SerializedName("commentCountTenThousandShow")
    private String commentCountTenThousandShow;
    /**
     * 是否点赞
     */
    @SerializedName("isLike")
    private Boolean isLike;
    /**
     * 创建时间
     */
    @SerializedName("createTimeMillis")
    private Long createTimeMillis;
    @SerializedName("picShareUrl")
    private String picShareUrl;
    @SerializedName("viewCountShow")
    private String viewCountShow;
    @SerializedName("commentCountShow")
    private String commentCountShow;
    @SerializedName("shareCountShow")
    private String shareCountShow;
    @SerializedName("stowCountShow")
    private String stowCountShow;
    @SerializedName("danmakuCountShow")
    private String danmakuCountShow;
    @SerializedName("bananaCountShow")
    private String bananaCountShow;
    @SerializedName("giftPeachCountShow")
    private String giftPeachCountShow;
    @SerializedName("likeCountShow")
    private String likeCountShow;
    @SerializedName("disableEdit")
    private Boolean disableEdit;
    @SerializedName("tagList")
    private List<TagListDTO> tagList;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private Integer status;
    @SerializedName("channel")
    private ChannelDTO channel;
    @SerializedName("isFavorite")
    private Boolean isFavorite;
    @SerializedName("superUbb")
    private Boolean superUbb;
    @SerializedName("isRewardSupportted")
    private Boolean isRewardSupportted;
    /**
     * 是否投蕉
     */
    @SerializedName("isThrowBanana")
    private Boolean isThrowBanana;

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
    public static class VideoListDTO {
        @SerializedName("priority")
        private Integer priority;
        @SerializedName("durationMillis")
        private Integer durationMillis;
        @SerializedName("danmakuCount")
        private Integer danmakuCount;
        @SerializedName("visibleType")
        private Integer visibleType;
        @SerializedName("uploadTime")
        private Long uploadTime;
        @SerializedName("title")
        private String title;
        @SerializedName("sourceStatus")
        private Integer sourceStatus;
        @SerializedName("danmakuCountShow")
        private String danmakuCountShow;
        @SerializedName("sizeType")
        private Integer sizeType;
        @SerializedName("danmakuGuidePosition")
        private Integer danmakuGuidePosition;
        @SerializedName("fileName")
        private String fileName;
        /**
         * 视频id
         */
        @SerializedName("id")
        private String id;
    }

    @NoArgsConstructor
    @Data
    public static class TagListDTO {
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;
    }

    public VideoBase parse2VideoBase() {
        VideoBase videoBase = new VideoBase();
        videoBase.setAc(this.contentId);
        videoBase.setTitle(this.contentTitle);
        if (CollectionUtils.isNotEmpty(this.videoList)) {
            videoBase.setId(this.videoList.get(0).getId());
        }
        videoBase.setType(this.contentType);
        videoBase.setCreateTime(this.createTimeMillis);
        videoBase.setIsThrowBanana(this.isThrowBanana);
        videoBase.setIsLike(this.isLike);
        videoBase.setSubChannelId(this.channel.parentId);
        videoBase.setSubChannelName(this.channel.parentName);
        videoBase.setUpId(this.userId);
        videoBase.setUpName(this.userName);
        return videoBase;
    }
}
