package com.zhuweitung.task;

import com.zhuweitung.api.AcFunApiHelper;
import com.zhuweitung.config.Config;
import com.zhuweitung.model.VideoBase;
import lombok.extern.log4j.Log4j2;

/**
 * 投蕉任务
 * @author zhuweitung
 * @create 2021/4/18
 */
@Log4j2
public class ThrowBananaTask extends AbstractTask {

    @Override
    public void run() {
        int throwBananaNum = Config.getInstance().getThrowBananaNum();
        if (AcFunApiHelper.getBananaBalance() == 0) {
            log.info("你的香蕉用完啦，跳过本次投蕉任务");
            return;
        }
        log.info("自定义投蕉为：{}根", throwBananaNum);
        while (throwBananaNum > 0) {
            try {
                if (AcFunApiHelper.getBananaBalance() == 0) {
                    log.info("你的香蕉用完啦，终止本次投蕉任务");
                }
                VideoBase videoBase = AcFunApiHelper.getRandomVideo(AcFunApiHelper.TYPE_BANANA);
                if (videoBase == null) {
                    log.debug("未找到可以投蕉的视频");
                    break;
                }
                int laveNum = AcFunApiHelper.throwBanana(throwBananaNum, videoBase);
                if (throwBananaNum == laveNum) {
                    log.debug("给视频：{} 投蕉失败", videoBase.getTitle());
                    break;
                }
                log.info("程序执行投蕉{}根，剩余{}根没投", throwBananaNum - laveNum, laveNum);
                throwBananaNum = laveNum;
                if (laveNum != 0) {
                    DailyTask.randomSuspend();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return "投蕉任务";
    }
}
