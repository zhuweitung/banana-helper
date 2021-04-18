package com.zhuweitung.task;

import com.zhuweitung.standard.IName;

/**
 * 任务抽象类
 * @author zhuweitung
 * @create 2021/4/18 
 */
public abstract class Task implements IName {

    public void run() {
    }

    @Override
    public String getName() {
        return "任务抽象类";
    }

    /**
     * @description 打印任务信息翻转
     * @param 
     * @return boolean
     * @author zhuweitung
     * @date 2021/4/18
     */
    public boolean printInfoReversal() {
        return false;
    }

}
