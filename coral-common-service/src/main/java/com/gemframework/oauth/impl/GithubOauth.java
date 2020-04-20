package com.gemframework.oauth.impl;

import com.gemframework.oauth.BaseOauth;
import org.springframework.stereotype.Component;

@Component
public class GithubOauth implements BaseOauth {

    private static final String GITHUB_CLIENT_ID = "e6e564e95b9fd05ea966";
    private static final String GITHUB_CLIENT_SECRET = "a975c860e2e507f3598d585cb9b1d47b3261aecb";

    /**
     * 登陆授权类型
     */
    @Override
    public String authorize() {
        return "https://github.com/login/oauth/authorize?client_id=" + GITHUB_CLIENT_ID + "&scope=user,public_repo";
    }

    @Override
    public String accessToken(String code) {
        return "https://github.com/login/oauth/access_token?client_id=" + GITHUB_CLIENT_ID + "&client_secret=" + GITHUB_CLIENT_SECRET + "&code=" + code;
    }

    @Override
    public String userInfo(String accessToken) {
        return "https://api.github.com/user?access_token=" + accessToken + "&scope=public_repo%2Cuser&token_type=bearer";
    }
}