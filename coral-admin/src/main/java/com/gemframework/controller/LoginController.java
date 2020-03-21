package com.gemframework.controller;

import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.request.UserLoginRequest;
import com.gemframework.utils.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.gemframework.model.enums.ErrorCode.LOGIN_FAIL;

@Slf4j
@Controller
public class LoginController {

    @PostMapping(value = "/login")
    @ResponseBody
    public BaseResultData login(UserLoginRequest loginRequest, HttpServletRequest request) {
        //验证码校验
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            return BaseResultData.ERROR(ErrorCode.VERIFY_CODE_ERROR);
        }else{
            log.info("验证码验证通过...");
        }
        // 创建主体
        Subject subject = SecurityUtils.getSubject();
        // 准备token
        UsernamePasswordToken token = new UsernamePasswordToken(loginRequest.getUsername(),loginRequest.getPassword());
        token.setRememberMe(loginRequest.isRememberMe());
        // 提交认证
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.info("未知账户");
        } catch (IncorrectCredentialsException ice) {
            log.info("密码不正确");
        } catch (LockedAccountException lae) {
            log.info("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            log.info("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            log.info("用户名或密码不正确");
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
        subject.logout();
        return "login";
    }
}