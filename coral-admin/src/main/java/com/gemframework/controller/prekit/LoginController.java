package com.gemframework.controller.prekit;

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

import static com.gemframework.model.enums.ErrorCode.*;

@Slf4j
@Controller
public class LoginController {

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
        subject.logout();
        return "login";
    }
}