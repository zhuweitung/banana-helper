package com.zhuweitung.signin;

/**
 * 登录后的一些账户信息
 * @author zhuweitung
 * @create 2021/4/18 
 */
public class Cookie {

    private static final Cookie COOKIE = new Cookie();

    private static String acPassToken;
    private static String authKey;

    public Cookie() {
    }

    public static void init(String acPassToken, String authKey) {
        Cookie.acPassToken = acPassToken;
        Cookie.authKey = authKey;
    }

    public static Cookie getInstance() {
        return COOKIE;
    }

    public String getAcPassToken() {
        return acPassToken;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getCookies() {
        return "acPasstoken=" + getAcPassToken()
                + ";auth_key=" + getAuthKey();
    }


}
