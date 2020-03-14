package com.gemframework.controller;

import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.request.UserLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public BaseResultData login(UserLoginRequest request) {
        //TODO:增加验证码校验

        // 创建主体
        Subject subject = SecurityUtils.getSubject();
        // 准备token
        UsernamePasswordToken token = new UsernamePasswordToken(request.getUsername(),request.getPassword());
        token.setRememberMe(request.isRememberMe());
        // 提交认证
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return BaseResultData.ERROR(-1,"未知账户");
        } catch (IncorrectCredentialsException ice) {
            return BaseResultData.ERROR(-3,"密码不正确");
        } catch (LockedAccountException lae) {
            return BaseResultData.ERROR(-4,"账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return BaseResultData.ERROR(-5,"用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return BaseResultData.ERROR(-6,"用户名或密码不正确");
        }
        if (subject.isAuthenticated()) {
            return BaseResultData.SUCCESS("登录成功");
        } else {
            token.clear();
            return BaseResultData.ERROR(-7,"登录失败");
        }
    }

    @GetMapping(value = "/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }


    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }
}