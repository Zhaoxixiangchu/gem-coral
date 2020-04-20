package com.gemframework.controller.prekit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gemframework.common.exception.GemException;
import com.gemframework.model.entity.po.Member;
import com.gemframework.model.enums.ChannelType;
import com.gemframework.model.enums.LoginType;
import com.gemframework.model.enums.ThirdPartyPlat;
import com.gemframework.model.enums.WhetherEnum;
import com.gemframework.model.response.GitXOAuthRespInfo;
import com.gemframework.model.response.GiteeOAuthRespInfo;
import com.gemframework.model.response.GithubOAuthRespInfo;
import com.gemframework.oauth.impl.GiteeServiceImpl;
import com.gemframework.oauth.impl.GithubServiceImpl;
import com.gemframework.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.gemframework.common.constant.GemConstant.System.DEF_PASSWORD;

@Slf4j
@Controller
@RequestMapping("oauth")
public class OAuthController {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private GiteeServiceImpl giteeService;

    @Autowired
    private GithubServiceImpl githubService;

    @Autowired
    private MemberService memberService;


    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "type",required = false) String type) {
        if(type == null || type.equals("")){
            throw new GemException("请选择登录类型");
        }
        if (type.toUpperCase().equals(LoginType.GITHUB.toString())) {
            return "redirect:" + githubService.authorizeUri();
        }
        return "redirect:" + giteeService.authorizeUri();
    }

    @GetMapping(value = "/github/callback")
    public String githubCallback(HttpServletRequest request, Model model) {
        try {
            String code = request.getParameter("code");
            JSONObject userInfo = githubService.getUserInfo(githubService.getAccessToken(code));
            GithubOAuthRespInfo info = JSON.parseObject(userInfo.toString(), GithubOAuthRespInfo.class);
            Member member = saveGithubMember(info);
            model.addAttribute("memberInfo", JSONObject.toJSON(member));
        }catch (GemException e){
            model.addAttribute("memberInfo", JSONObject.toJSON(e));
        }
        return "modules/prekit/demo/oauth";
    }

    @GetMapping(value = "/gitee/callback")
    public String giteeCallBack(HttpServletRequest request, Model model) {
        try {
            String code = request.getParameter("code");
            JSONObject userInfo = giteeService.getUserInfo(giteeService.getAccessToken(code));
            GiteeOAuthRespInfo info = JSON.parseObject(userInfo.toString(), GiteeOAuthRespInfo.class);
            Member member = saveGiteeMember(info);
            model.addAttribute("memberInfo", JSONObject.toJSON(member));
        }catch (GemException e){
            model.addAttribute("memberInfo", JSONObject.toJSON(e));
        }
        return "modules/prekit/demo/oauth";
    }


    private Member saveGiteeMember(GiteeOAuthRespInfo info){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account",info.getLogin());
        Member member = memberService.getOne(queryWrapper);
        if(member == null){
            member = new Member();
        }
        member.setAccount(info.getLogin());
        member.setPassword(DEF_PASSWORD);
        member.setAvatarUrl(info.getAvatarUrl());
        member.setNickname(info.getName());
        member.setBlog(info.getBlog());
        member.setRemark(info.getBio());
        member.setStatus(WhetherEnum.NO.getCode());
        member.setIsOauth(WhetherEnum.YES.getCode());
        member.setThirdParty(info.getThirdParty());
        member.setChannel(ChannelType.OAUTH.getCode());
        member.setLastLoginTime(new Date());
        memberService.saveOrUpdate(member);
        return member;
    }

    private Member saveGithubMember(GithubOAuthRespInfo info){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account",info.getLogin());
        Member member = memberService.getOne(queryWrapper);
        if(member == null){
            member = new Member();
        }
        member.setAccount(info.getLogin());
        member.setPassword(DEF_PASSWORD);
        member.setAvatarUrl(info.getAvatarUrl());
        member.setNickname(info.getName());
        member.setBlog(info.getBlog());
        member.setRemark(info.getBio());
        member.setStatus(WhetherEnum.NO.getCode());
        member.setIsOauth(WhetherEnum.YES.getCode());
        member.setThirdParty(info.getThirdParty());
        member.setChannel(ChannelType.OAUTH.getCode());
        member.setLastLoginTime(new Date());
        memberService.saveOrUpdate(member);
        return member;
    }

}