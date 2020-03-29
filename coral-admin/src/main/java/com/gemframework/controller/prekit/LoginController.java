/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.controller.prekit;

import com.gemframework.constant.GemRedisKes;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.request.UserLoginRequest;
import com.gemframework.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Set;

import static com.gemframework.model.enums.ErrorCode.*;

@Slf4j
@Controller
public class LoginController extends BaseController{

    @PostMapping(value = "/login")
    @ResponseBody
    public BaseResultData login(UserLoginRequest loginRequest, HttpServletRequest request) {
        //验证码校验
        if(!VerifyCodeUtils.checkVerifyCode(request)){
            return BaseResultData.ERROR(ErrorCode.VERIFY_CODE_ERROR);
        }else{
            log.info("验证码验证通过...");
        }
        // 创建主体
        Subject subject = SecurityUtils.getSubject();
        // 准备token
        UsernamePasswordToken token = new UsernamePasswordToken(loginRequest.getUsername(),loginRequest.getPassword(),loginRequest.isRememberMe());
        try {
        // 提交认证
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return BaseResultData.ERROR(LOGIN_FAIL_UNKNOWNACCOUNT);
        } catch (IncorrectCredentialsException ice) {
            return BaseResultData.ERROR(LOGIN_FAIL_INCORRECTCREDENTIALS);
        } catch (LockedAccountException lae) {
            return BaseResultData.ERROR(LOGIN_FAIL_LOCKEDACCOUNT);
        } catch (ExcessiveAttemptsException eae) {
            return BaseResultData.ERROR(LOGIN_FAIL_EXCESSIVEATTEMPTS);
        } catch (AuthenticationException ae) {
            return BaseResultData.ERROR(LOGIN_FAIL_AUTHENTICATION);
        }
        if (subject.isAuthenticated()) {
            log.info("登录成功...");
            return BaseResultData.SUCCESS("登录成功");
        } else {
            token.clear();
            return BaseResultData.ERROR(LOGIN_FAIL);
        }
    }

    @GetMapping(value = "/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        //如果开启Redis注销所有keys缓存
        if(gemRedisProperties.isOpen()){
            String pattern = subject.getPrincipals() +"_"+ GemRedisKes.Auth.PREFIX + "*";
            Set<String> keys = gemRedisUtils.keys(pattern);
            log.info("注销所有keys="+keys);
            gemRedisUtils.delete(keys);
        }
        subject.logout();
        return "login";
    }
}