package com.zhuweitung.task;

import com.zhuweitung.signin.Acer;

/**
 * 初始化Acer对象
 * @author zhuweitung
 * @create 2021/4/18 
 */
public class InitAcerTask extends Task {

    @Override
    public void run() {
        Acer.init();
    }

    @Override
    public String getName() {
        return "给Acer new个对象";
    }

    @Override
    public boolean printInfoReversal() {
        return true;
    }
}
