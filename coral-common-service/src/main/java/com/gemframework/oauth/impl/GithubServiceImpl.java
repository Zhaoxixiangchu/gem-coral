package com.gemframework.oauth.impl;

import com.alibaba.fastjson.JSONObject;
import com.gemframework.oauth.BaseOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class GithubServiceImpl implements BaseOauthService {

    @Autowired
    GithubOauth githubOauth;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String authorizeUri() {
        return githubOauth.authorize();
    }

    @Override
    public String getAccessToken(String code) {
        String token = githubOauth.accessToken(code);
        ResponseEntity<Object> forEntity = restTemplate.exchange(token, HttpMethod.GET, httpEntity(), Object.class);
        String[] split = Objects.requireNonNull(forEntity.getBody()).toString().split("=");
        String accessToken = split[1].split(",")[0];
        return accessToken;
    }

    @Override
    public JSONObject getUserInfo(String accessToken) {
        String userInfo = githubOauth.userInfo(accessToken);
        ResponseEntity<JSONObject> entity = restTemplate.exchange(userInfo, HttpMethod.GET, httpEntity(), JSONObject.class);
        JSONObject body = entity.getBody();
        return body;
    }

    public static HttpEntity httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
        return request;
    }
}