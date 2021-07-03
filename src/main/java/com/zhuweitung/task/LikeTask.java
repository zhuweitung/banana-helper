package com.zhuweitung.task;

import com.zhuweitung.api.AcFunApiHelper;
import com.zhuweitung.model.VideoBase;
import lombok.extern.log4j.Log4j2;

/**
 * 点赞任务
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
public class LikeTask extends AbstractTask {

    @Override
    public void run() {
        VideoBase videoBase = AcFunApiHelper.getRandomVideo(AcFunApiHelper.TYPE_LIKE);
        if (videoBase == null) {
            log.info("未找到可以点赞的视频");
        } else {
            AcFunApiHelper.like(videoBase);
        }
    }

    @Override
    public String getName() {
        return "点赞任务";
    }
}
