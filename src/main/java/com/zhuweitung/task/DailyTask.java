package com.zhuweitung.task;

import com.zhuweitung.signin.Acer;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 每日任务
 * @author zhuweitung
 * @create 2021/4/18
 */
@Log4j2
public class DailyTask {

    private final List<AbstractTask> dailyTasks;

    public DailyTask() {
        dailyTasks = new ArrayList<>();
        dailyTasks.add(new InitAcerTask());
        dailyTasks.add(new SignTask());
        dailyTasks.add(new ThrowBananaTask());
        dailyTasks.add(new DanmuTask());
        dailyTasks.add(new LikeTask());
        dailyTasks.add(new ShareTask());
    }

    /**
     * @description 运行每日任务
     * @param
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public void runDailyTask() {
        try {
            printTime();
            log.debug("任务启动中...");
            int taskIndex = 0;
            for (AbstractTask task : dailyTasks) {
                if (task.printInfoReversal()) {
                    log.info("------开始{}------", task.getName());
                } else {
                    log.info("------{}开始------", task.getName());
                }
                task.run();
                if (task.printInfoReversal()) {
                    log.info("------结束{}------\n", task.getName());
                } else {
                    log.info("------{}结束------\n", task.getName());
                }
                if (taskIndex < dailyTasks.size() - 1) {
                    randomSuspend();
                }
                taskIndex++;
            }
            log.info("今日任务已全部执行完毕");
            printAcFunAccDays();
            log.info("AC在，爱一直在！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //消息推送
            ServerPush.doServerPush();
        }
    }

    /**
     * @description 打印当前时间
     * @param
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    private void printTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(d);
        log.info(time);
    }

    /**
     * @description 随机暂停
     * @param
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void randomSuspend() throws InterruptedException {
        Random random = new Random();
        int sleepTime = (int) ((random.nextDouble() + 0.1) * 3000);
        log.info("-----随机暂停{}ms-----\n", sleepTime);
        Thread.sleep(sleepTime);
    }

    /**
     * @description 打印陪伴AcFun的时间
     * @param
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void printAcFunAccDays() {
        long currentTime = System.currentTimeMillis();
        long registerTime = Acer.getInstance().getRegisterTime();
        long days = TimeUnit.DAYS.convert(currentTime - registerTime , TimeUnit.MILLISECONDS);
        log.info("您已经陪伴AC娘{}个日夜！以后也请多多关照~", days);
    }

}
