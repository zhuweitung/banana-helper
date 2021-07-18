package com.zhuweitung.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zhuweitung.config.Config;
import com.zhuweitung.model.Danmu;
import com.zhuweitung.model.VideoBase;
import com.zhuweitung.model.VideoRank;
import com.zhuweitung.model.VideoTrend;
import com.zhuweitung.signin.Acer;
import com.zhuweitung.signin.Token;
import com.zhuweitung.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * AcFun API常用工具类
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
public class AcFunApiHelper {

    public static final String DEFAULT_DANMU = "第一";

    /**
     * 每个视频投蕉数
     */
    public static final int PER_VIDEO_BANANA_NUM = 5;

    public static final int TYPE_BANANA = 1;
    public static final int TYPE_DANMU = 2;
    public static final int TYPE_LIKE = 3;

    /**
     * 香蕉榜视频
     */
    private static List<VideoBase> rankVideos = new ArrayList<>();
    /**
     * 24小时内关注up视频动态
     */
    private static List<VideoBase> trendVideos = new ArrayList<>();

    /**
     * 已投蕉AC号集合
     */
    private static List<Integer> throwBananaedList = new ArrayList<>();
    /**
     * 已发弹幕AC号集合
     */
    private static List<Integer> danmuedList = new ArrayList<>();
    /**
     * 已点赞AC号集合
     */
    private static List<Integer> likedList = new ArrayList<>();

    /**
     * @description 初始化token
     * @param
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void initToken() {
        JsonObject responseJson = HttpUtil.doPost(AcFunApi.GET_TOKEN.getUrl(), "sid=acfun.midground.api");
        int responseCode = responseJson.get("result").getAsInt();
        if (responseCode == 0) {
            String ssecurity = responseJson.get("ssecurity").getAsString();
            Integer userId = responseJson.get("userId").getAsInt();
            String acfunMidgroundApiSt = responseJson.get("acfun.midground.api_st").getAsString();
            String acfunMidgroundApiAt = responseJson.get("acfun.midground.api.at").getAsString();
            Token.init(ssecurity, userId, acfunMidgroundApiSt, acfunMidgroundApiAt);
        } else {
            log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.GET_TOKEN.getName(), responseJson);
        }
    }

    /**
     * @description 获取剩余香蕉
     * @param
     * @return int
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static int getBananaBalance() {
        Acer acer = Acer.getInstance();
        return acer.getBanana();
    }

    /**
     * @description 获取可以投的香蕉数
     * @param laveNum 剩余投蕉数
     * @return int
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static int throwBanana(int laveNum, VideoBase videoBase) {
        int throwNum = Math.min(getBananaBalance(), PER_VIDEO_BANANA_NUM);

        Map<String, String> params = new HashMap<>();
        params.put("resourceId", videoBase.getAc() + "");
        params.put("count", PER_VIDEO_BANANA_NUM + "");
        params.put("resourceType", videoBase.getType() + "");
        Properties properties = new Properties();
        properties.setProperty("referer", "https://www.acfun.cn/v/ac" + videoBase.getAc());
        JsonObject responseJson = HttpUtil.doPost(AcFunApi.VIDEO_THROWBANANA.getUrl(), params, properties);
        int responseCode = responseJson.get("result").getAsInt();
        if (responseCode == 0) {
            log.info("在视频：{}，给{}喂了{}根大香蕉~", videoBase.getTitle(), videoBase.getUpName(), PER_VIDEO_BANANA_NUM);
            laveNum -= throwNum;
            addVideo(TYPE_BANANA, videoBase.getAc());

            //点赞
            if (Config.getInstance().getThrowAndLike() == 1) {
                like(videoBase);
            }

            //发送弹幕
            if (Config.getInstance().getThrowAndDanmu() == 1) {
                danmu(videoBase);
            }

        } else {
            log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.VIDEO_THROWBANANA.getName(), responseJson);
        }

        return laveNum;
    }

    /**
     * @description 点赞
     * @param videoBase
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void like(VideoBase videoBase) {
        if (Config.getInstance().getIsLike() == 1) {
            Map<String, String> params = new HashMap<>();
            params.put("objectId", videoBase.getAc() + "");
            params.put("objectType", videoBase.getType() + "");
            params.put("interactType", "1");
            params.put("subBiz", "mainApp");
            params.put("acfun.midground.api_st", Token.getInstance().getAcfunMidgroundApiSt());
            params.put("kpn", "ACFUN_APP");
            JsonObject responseJson = HttpUtil.doPost(AcFunApi.VIDEO_LIKE.getUrl(), params);
            int responseCode = responseJson.get("result").getAsInt();
            if (responseCode == 1) {
                log.info("给视频：{} 点了个赞 ，收到点赞后{}当场来了个后空翻~", videoBase.getTitle(), videoBase.getUpName());
                addVideo(TYPE_LIKE, videoBase.getAc());
            } else {
                log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.VIDEO_LIKE.getName(), responseJson);
            }
        } else {
            log.info("不需要点赞，跳过任务");
        }
    }

    /**
     * @description 发送弹幕
     * @param videoBase
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void danmu(VideoBase videoBase) {
        if (Config.getInstance().getIsDanmu() == 1) {
            Danmu danmu = getRandomDanmu(videoBase);
            Map<String, String> params = new HashMap<>();
            params.put("body", danmu.getBody());
            params.put("color", "16777215");
            params.put("id", videoBase.getAc() + "");
            params.put("mode", "1");
            params.put("position", danmu.getPosition() + "");
            params.put("size", "25");
            params.put("subChannelId", videoBase.getSubChannelId() + "");
            params.put("subChannelName", videoBase.getSubChannelName() + "");
            params.put("type", "douga");
            params.put("videoId", videoBase.getId() + "");
            Properties properties = new Properties();
            properties.setProperty("referer", "https://www.acfun.cn/v/ac" + videoBase.getAc());
            JsonObject responseJson = HttpUtil.doPost(AcFunApi.VIDEO_SENDDANMU.getUrl(), params, properties);
            int responseCode = responseJson.get("result").getAsInt();
            if (responseCode == 0) {
                log.info("给视频：{} 发了条弹幕 {}", videoBase.getTitle(), danmu.getBody());
                addVideo(TYPE_DANMU, videoBase.getAc());
            } else {
                log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.VIDEO_SENDDANMU.getName(), responseJson);
            }
        } else {
            log.info("不需要发弹幕，跳过任务");
        }
    }


    /**
     * @description 随机获取视频
     * @param type
     * @return com.zhuweitung.model.VideoBase
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static VideoBase getRandomVideo(Integer type) {
        List<VideoBase> list = new ArrayList<>();
        if (Config.getInstance().getBananaPriority() == 1) {
            CollectionUtils.addAll(list, getTrendVideos().toArray(new VideoBase[trendVideos.size()]));
        }
        //防止账号没有关注的up
        if (CollectionUtils.isEmpty(list)) {
            CollectionUtils.addAll(list, getRankVideos().toArray(new VideoBase[rankVideos.size()]));
        }
        List<VideoBase> videos = filterVideo(type, list);

        VideoBase video = null;
        //先获取优先级高的up视频
        List<Integer> followUpPriority = Config.getInstance().getFollowUpPriority();
        for (Integer upId : followUpPriority) {
            for (VideoBase videoBase : videos) {
                if (upId.equals(videoBase.getUpId())) {
                    video = videoBase;
                    break;
                }
            }
        }

        //再随机获取视频
        if (video == null && CollectionUtils.isNotEmpty(videos)) {
            video = videos.get((int) (Math.random() * videos.size()));
        }

        return video;
    }

    /**
     * @description 过滤已操作过的视频
     * @param type
     * @param list
     * @return java.util.List<com.zhuweitung.model.VideoBase>
     * @author zhuweitung
     * @date 2021/4/18
     */
    private static List<VideoBase> filterVideo(Integer type, List<VideoBase> list) {
        List<VideoBase> videos = new ArrayList<>();
        list.forEach(videoBase -> {
            switch (type) {
                case TYPE_BANANA:
                    if (!throwBananaedList.contains(videoBase.getAc())) {
                        videos.add(videoBase);
                    }
                    break;
                case TYPE_DANMU:
                    if (!danmuedList.contains(videoBase.getAc())) {
                        videos.add(videoBase);
                    }
                    break;
                case TYPE_LIKE:
                    if (!likedList.contains(videoBase.getAc())) {
                        videos.add(videoBase);
                    }
                    break;
                default:
                    break;
            }
        });
        return videos;
    }

    /**
     * @description 将AC号加入已操作列表
     * @param type
     * @param ac ac号
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void addVideo(Integer type, Integer ac) {
        switch (type) {
            case TYPE_BANANA:
                throwBananaedList.add(ac);
                break;
            case TYPE_DANMU:
                danmuedList.add(ac);
                break;
            case TYPE_LIKE:
                likedList.add(ac);
                break;
            default:
                break;
        }
    }

    /**
     * @description 获取香蕉榜视频列表
     * @param
     * @return java.util.List<com.zhuweitung.model.VideoBase>
     * @author zhuweitung
     * @date 2021/4/18
     */
    private static List<VideoBase> getRankVideos() {
        if (CollectionUtils.isEmpty(rankVideos)) {
            JsonObject responseJson = HttpUtil.doPost(AcFunApi.GET_BANANALIST.getUrl(), "channelId=0&rankPeriod=DAY");
            int responseCode = responseJson.get("result").getAsInt();
            if (responseCode == 0) {
                ArrayList<VideoRank> list = new Gson().fromJson(responseJson.getAsJsonArray("rankList"), new TypeToken<ArrayList<VideoRank>>() {
                }.getType());
                if (CollectionUtils.isNotEmpty(list)) {
                    list.forEach(videoRank -> {
                        VideoBase videoBase = videoRank.parse2VideoBase();
                        rankVideos.add(videoBase);
                        if (videoBase.getIsThrowBanana()) {
                            throwBananaedList.add(videoBase.getAc());
                        }
                        if (videoBase.getIsLike()) {
                            likedList.add(videoBase.getAc());
                        }
                    });
                }
            } else {
                log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.GET_BANANALIST.getName(), responseJson);
            }
        }
        return rankVideos;
    }

    /**
     * @description 获取关注up视频动态列表（24小时内）
     * @param
     * @return java.util.List<com.zhuweitung.model.VideoBase>
     * @author zhuweitung
     * @date 2021/4/18
     */
    private static List<VideoBase> getTrendVideos() {
        if (CollectionUtils.isEmpty(trendVideos)) {
            long currentTime = System.currentTimeMillis();
            //24小时前
            long minTime = currentTime - 24 * 60 * 60 * 1000;
            boolean is24Hour = false;

            Map<String, String> params = new HashMap<>();
            params.put("count", "60");
            params.put("pcursor", "0");
            while (!is24Hour) {
                JsonObject responseJson = HttpUtil.doGet(AcFunApi.GET_FOLLOWUPVIDEOTREND.getUrl(), params);
                int responseCode = responseJson.get("result").getAsInt();
                if (responseCode == 0) {
                    ArrayList<VideoTrend> list = new Gson().fromJson(responseJson.getAsJsonArray("feedList"), new TypeToken<ArrayList<VideoTrend>>() {
                    }.getType());
                    if (CollectionUtils.isNotEmpty(list)) {
                        list.forEach(videoTrend -> {
                            VideoBase videoBase = videoTrend.parse2VideoBase();
                            //过滤文章动态、24小时外、配置了跳过的视频
                            boolean isVideo = videoBase.getType().equals(2);
                            boolean notOverDate = videoBase.getCreateTime() > minTime;
                            boolean notSkip = !skip(videoBase.getUpId());
                            if (isVideo && notOverDate && notSkip) {
                                trendVideos.add(videoBase);
                                if (videoBase.getIsThrowBanana()) {
                                    throwBananaedList.add(videoBase.getAc());
                                }
                                if (videoBase.getIsLike()) {
                                    likedList.add(videoBase.getAc());
                                }
                            }
                        });
                        VideoBase lastVideo = list.get(list.size() - 1).parse2VideoBase();
                        if (lastVideo.getCreateTime() > minTime) {
                            params.put("pcursor", (lastVideo.getCreateTime() + 1) + "");
                        } else {
                            is24Hour = true;
                        }
                    } else {
                        is24Hour = true;
                    }
                } else {
                    log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.GET_FOLLOWUPVIDEOTREND.getName(), responseJson);
                    break;
                }
            }
        }
        return trendVideos;
    }

    /**
     * @description 关注的up是否需要跳过
     * @param upId
     * @return boolean
     * @author zhuweitung
     * @date 2021/4/21
     */
    private static boolean skip(Integer upId) {
        List<Integer> skipUpList = Config.getInstance().getSkipUpList();
        if (CollectionUtils.isEmpty(skipUpList)) {
            return false;
        }
        return upId == null || skipUpList.contains(upId);
    }

    /**
     * @description 获取随机弹幕
     * @param video
     * @return java.lang.String
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static Danmu getRandomDanmu(VideoBase video) {
        Danmu danmu = new Danmu();
        List<String> danmuBodys = new ArrayList<>();
        if (Config.getInstance().getDanmuPriority() == 1) {
            danmuBodys = Config.getInstance().getDanmuPool();
        }
        if (CollectionUtils.isEmpty(danmuBodys)) {
            List<Danmu> danmus = new ArrayList<>();
            Map<String, String> params = new HashMap<>();
            int page = 1;
            params.put("resourceId", video.getId());
            params.put("resourceType", "9");
            params.put("enableAdvanced", "false");
            params.put("pcursor", page + "");
            params.put("count", "1000");
            params.put("sortType", "1");
            params.put("asc", "false");
            boolean end = false;
            while (!end) {
                JsonObject responseJson = HttpUtil.doPost(AcFunApi.GET_VIDEODANMULIST.getUrl(), params);
                int responseCode = responseJson.get("result").getAsInt();
                if (responseCode == 0) {
                    int totalCount = responseJson.get("totalCount").getAsInt();
                    ArrayList<Danmu> list = new Gson().fromJson(responseJson.getAsJsonArray("danmakus"), new TypeToken<ArrayList<Danmu>>() {
                    }.getType());
                    if (CollectionUtils.isNotEmpty(list)) {
                        danmus.addAll(list);
                        if (danmus.size() != totalCount) {
                            params.put("pcursor", (++page) + "");
                        } else {
                            end = true;
                        }
                    } else {
                        end = true;
                    }
                } else {
                    log.debug("请求{}接口出错，请稍后重试。错误请求信息：{}", AcFunApi.GET_VIDEODANMULIST.getName(), responseJson);
                    break;
                }
            }

            Map<String, List<Danmu>> danmuCountMap = new HashMap<>();
            for (Danmu _danmu : danmus) {
                List<Danmu> list = danmuCountMap.getOrDefault(_danmu.getBody(), new ArrayList<>());
                list.add(_danmu);
                danmuCountMap.put(_danmu.getBody(), list);
            }
            //排序
            List<Map.Entry<String, List<Danmu>>> entries = new ArrayList<>(danmuCountMap.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<String, List<Danmu>>>() {
                @Override
                public int compare(Map.Entry<String, List<Danmu>> t0, Map.Entry<String, List<Danmu>> t1) {
                    return t1.getValue().size() - t0.getValue().size();
                }
            });

            if (CollectionUtils.isNotEmpty(entries)) {
                danmu.setBody(entries.get(0).getValue().get(0).getBody());
                danmu.setPosition(entries.get(0).getValue().get(0).getPosition());
                return danmu;
            }
        }

        danmu.setPosition(1000);
        if (CollectionUtils.isNotEmpty(danmuBodys)) {
            danmu.setBody(danmuBodys.get((int) (Math.random() * danmuBodys.size())));
        } else {
            danmu.setBody(DEFAULT_DANMU);
        }
        return danmu;
    }

}
