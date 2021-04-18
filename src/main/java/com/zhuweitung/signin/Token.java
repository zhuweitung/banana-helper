package com.zhuweitung.signin;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token
 * @author zhuweitung
 * @create 2021/4/18 
 */
@NoArgsConstructor
@Data
public class Token {

    private static final Token TOKEN = new Token();

    private static String ssecurity;
    private static Integer userId;
    private static String acfunMidgroundApiSt;
    private static String acfunMidgroundApiAt;

    public static void init(String ssecurity, Integer userId, String acfunMidgroundApiSt, String acfunMidgroundApiAt) {
        Token.ssecurity = ssecurity;
        Token.userId = userId;
        Token.acfunMidgroundApiSt = acfunMidgroundApiSt;
        Token.acfunMidgroundApiAt = acfunMidgroundApiAt;
    }

    public String getAcfunMidgroundApiSt() {
        return acfunMidgroundApiSt;
    }

    public static Token getInstance() {
        return TOKEN;
    }
}
