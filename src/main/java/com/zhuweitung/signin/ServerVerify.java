package com.zhuweitung.signin;

import lombok.Getter;

/**
 * @author Junzhou Liu
 * @create 2020/10/21 19:57
 */
@Getter
public class ServerVerify {

    private final static ServerVerify SERVER_VERIFY = new ServerVerify();
    private static String ftkey = null;
    private static String chatId = null;

    public static void verifyInit(String ftKey) {
        ServerVerify.ftkey = ftKey;
    }

    public static void verifyInit(String ftKey, String chatId) {
        ServerVerify.ftkey = ftKey;
        ServerVerify.chatId = chatId;
    }

    public static String getFtkey() {
        return ftkey;
    }

    public static String getChatId() {
        return chatId;
    }

    public static ServerVerify getInstance() {
        return SERVER_VERIFY;
    }
}
