package com.zhuweitung.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author zhuweitung
 * @create 2021/4/18 
 */
public class StringUtil {

    /**
     * @description 判断字符串是否是json对象
     * @param result
     * @return boolean
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static boolean isJsonObj(String result){
        if(StringUtils.isNotBlank(result)){
            return result.startsWith("{");
        }
        return false;
    }

    /**
     * @description 判断字符串是否是json数组
     * @param result
     * @return boolean
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static boolean isJsonArray(String result) {
        if (StringUtils.isNotBlank(result)) {
            return result.startsWith("[");
        }
        return false;
    }

    /**
     * @description 判断字符串是否是json
     * @param result
     * @return boolean
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static boolean isJsonString(String result) {
        return isJsonObj(result) || isJsonArray(result);
    }
}
