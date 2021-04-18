package com.zhuweitung.signin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.zhuweitung.api.AcFunApi;
import com.zhuweitung.util.HttpUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

/**
 * Acer 账户基本信息
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
@NoArgsConstructor
@Data
public class Acer implements Serializable {

    @SerializedName("renameCard")
    private Integer renameCard;
    @SerializedName("isEmailCheck")
    private Boolean isEmailCheck;
    @SerializedName("isMobileCheck")
    private Boolean isMobileCheck;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("activeRecently")
    private Boolean activeRecently;
    @SerializedName("signIn")
    private Boolean signIn;
    @SerializedName("headUrl")
    private String headUrl;
    @SerializedName("verifiedType")
    private Integer verifiedType;
    @SerializedName("verifiedText")
    private String verifiedText;
    @SerializedName("nameRed")
    private Boolean nameRed;
    @SerializedName("avatarFrame")
    private Integer avatarFrame;
    @SerializedName("avatarFrameMobileImg")
    private String avatarFrameMobileImg;
    @SerializedName("avatarFramePcImg")
    private String avatarFramePcImg;
    @SerializedName("contentCount")
    private String contentCount;
    @SerializedName("nameColor")
    private Integer nameColor;
    @SerializedName("verifiedTypes")
    private List<?> verifiedTypes;
    @SerializedName("experimentConfig")
    private String experimentConfig;
    @SerializedName("gender")
    private Integer gender;
    @SerializedName("shareUrl")
    private String shareUrl;
    @SerializedName("registerTime")
    private Long registerTime;
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("goldBanana")
    private Integer goldBanana;
    @SerializedName("followed")
    private String followed;
    @SerializedName("following")
    private String following;
    @SerializedName("firstDepositState")
    private Integer firstDepositState;
    @SerializedName("banana")
    private Integer banana;
    @SerializedName("isContractUp")
    private Boolean isContractUp;
    @SerializedName("isRegular")
    private Boolean isRegular;
    @SerializedName("followedNum")
    private Integer followedNum;
    @SerializedName("tagStowCount")
    private String tagStowCount;
    @SerializedName("isTeenagerMode")
    private Boolean isTeenagerMode;
    @SerializedName("isSameCityTagAllowShown")
    private Boolean isSameCityTagAllowShown;
    @SerializedName("allowShowLikeList")
    private Boolean allowShowLikeList;
    @SerializedName("mediaWearInfo")
    private MediaWearInfoDTO mediaWearInfo;
    @SerializedName("userName")
    private String userName;
    @SerializedName("blog")
    private String blog;
    @SerializedName("qq")
    private String qq;
    @SerializedName("comeFrom")
    private String comeFrom;
    @SerializedName("sexTrend")
    private Integer sexTrend;
    @SerializedName("signature")
    private String signature;
    @SerializedName("level")
    private Integer level;

    @NoArgsConstructor
    @Data
    public static class MediaWearInfoDTO {
        @SerializedName("uperId")
        private Integer uperId;
        @SerializedName("uperName")
        private String uperName;
        @SerializedName("uperHeadUrl")
        private String uperHeadUrl;
        @SerializedName("clubName")
        private String clubName;
        @SerializedName("level")
        private Integer level;
    }

    public static Acer ACER = new Acer();

    public static void init() {
        JsonObject responseJson = HttpUtil.doPost(AcFunApi.GET_PERSONINFO.getUrl());
        int result = responseJson.get("result").getAsInt();
        if (result == 0) {
            ACER = new Gson().fromJson(responseJson.get("info"), Acer.class);
            log.info("Acer有对♂象啦~");
            log.info("Acer昵称：{}", ACER.getUserName());
            log.info("香♂蕉数：{}", ACER.getBanana());
            log.info("金♂香♂蕉数：{}", ACER.getGoldBanana());
        } else {
            log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.GET_PERSONINFO.getName(), responseJson);
        }
    }

    public static Acer getInstance() {
        return ACER;
    }

}
