package com.gemframework.oauth.impl;

import com.gemframework.oauth.BaseOauth;
import org.springframework.stereotype.Component;

@Component
public class GiteeOauth implements BaseOauth {

    private static final String GITEE_CLIENT_ID = "1238dc9601519292e7d6e79f8d97fd13bfdb772bd47062d6253bb85011c3a9b2";
    private static final String GITEE_CLIENT_SECRET = "50d95be37acae4d3dbf0b802e7159481ae4866ead78553f28bea700019e9c36d";

    private static final String REDIRECT_URI = "http://fs.yxsdcti.com:8088/admin/oauth/gitee/callback";

    @Override
    public String authorize() {
        return "https://gitee.com/oauth/authorize?client_id=" + GITEE_CLIENT_ID + "&response_type=code&redirect_uri=" + REDIRECT_URI;
    }

    @Override
    public String accessToken(String code) {
        return "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=" + GITEE_CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&client_secret=" + GITEE_CLIENT_SECRET;
    }

    @Override
    public String userInfo(String accessToken) {
        return "https://gitee.com/api/v5/user?access_token=" + accessToken;
    }
}