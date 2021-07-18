package com.zhuweitung;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 * 项目信息
 * @author zhuweitung
 * @create 2021/4/18 
 */
@Log4j2
public class ProjectInfo {

    private static String updateDate = "2021-04-18";
    private static String projectRepo = "https://github.com/zhuweitung/banana-helper.git";
    private static String releaseVersion = "0.2.8";
    private static String updateInfo = "";
    private static String acfunMemberUrl = "https://www.acfun.cn/u/625724";

    public static void printInfo() {
        log.info("-----项目信息-----");
        log.info("当前版本: " + releaseVersion);
        log.info("最后更新日期: " + updateDate);
        if (StringUtils.isNotBlank(updateInfo)) {
            log.info("最后更新内容: " + updateDate);
        }
        log.info("项目开源地址: " + projectRepo);
        log.info("AcFun主页: " + acfunMemberUrl);
        log.info("-----项目信息-----\n");
    }
}
