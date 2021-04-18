package com.zhuweitung.config;

import org.junit.Test;

/**
 * 【这里填充类的作用说明】
 * @author zhuweitung
 * @create 2021/4/18 
 */
public class ConfigTest {

    @Test
    public void init() {

        Config.getInstance().init();
        System.out.println(Config.getInstance().toString());

    }
}