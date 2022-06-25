package com.zhuweitung;

import com.zhuweitung.api.AcFunApiHelper;
import com.zhuweitung.config.Config;
import com.zhuweitung.signin.Cookie;
import com.zhuweitung.signin.ServerVerify;
import com.zhuweitung.task.DailyTask;
import lombok.extern.log4j.Log4j2;

/**
 * 任务运行入口
 * @author zhuweitung
 * @create 2021/4/18
 */
@Log4j2
public class BananaMain {

    public static void main(String[] args) {

        if (args.length < 2) {
            log.info("任务启动失败");
            log.warn("Cookies参数缺失，请检查是否在Github Secrets中配置acPassToken和authKey参数");
            return;
        }
        //读取环境变量
        Cookie.init(args[0], args[1]);

        if (args.length > 3) {
            ServerVerify.verifyInit(args[2], args[3]);
        } else if (args.length > 2) {
            ServerVerify.verifyInit(args[2]);
        }

        //初始化配置
        Config.getInstance().init();

        //初始化token
        AcFunApiHelper.initToken();

        ProjectInfo.printInfo();
        //执行今日任务
        DailyTask dailyTask = new DailyTask();
        dailyTask.runDailyTask();

    }

}
