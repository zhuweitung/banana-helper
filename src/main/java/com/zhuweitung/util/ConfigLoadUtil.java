package com.zhuweitung.util;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 配置文件加载工具类
 * @author zhuweitung
 * @create 2021/4/18
 */
@Log4j2
public class ConfigLoadUtil {

    /**
     * @description 读取自定义配置文件
     * linux用户可能用到
     * @param
     * @return java.lang.String
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static String loadCustomConfig() {
        String config = null;
        try {
            String configPath = System.getProperty("user.dir") + File.separator + "config.json" + File.separator;
            InputStream is = new FileInputStream(configPath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            config = new String(buffer, StandardCharsets.UTF_8);
            log.info("读取外部配置文件成功");
        } catch (FileNotFoundException e) {
            log.info("未扫描到外部配置文件，即将加载默认配置文件【此提示仅针自行部署的Linux用户，普通用户请忽略】");
        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e);
        }
        return config;
    }


    /**
     * @description 读取项目配置文件
     * @return java.lang.String
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static String loadProjectConfig() {
        String json = null;
        try {
            InputStream is = ConfigLoadUtil.class.getClassLoader().getResourceAsStream("config.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
            log.info("读取内部配置文件成功");
        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e);
        }
        return json;
    }

    /**
     * @description 读取文件内容
     * @param filePath 读入的文件路径
     * @return java.lang.String
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static String loadFile(String filePath) {
        String logs = null;
        try {
            InputStream is = new FileInputStream(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            logs = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e);
        }
        return logs;
    }
}
