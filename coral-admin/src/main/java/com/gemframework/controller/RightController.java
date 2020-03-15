package com.gemframework.controller;
import com.gemframework.model.entity.po.User;
import com.gemframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Title: RightController
 * @Package: com.gemframework.controller
 * @Date: 2020-03-15 13:34:48
 * @Version: v1.0
 * @Description: 权限信息控制器
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class RightController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    @ResponseBody
    public String getAll() {
        List<User> list = userService.list();
        System.out.println("list:" + list);
        return list.toString();
    }

    @RequestMapping("/insert")
    @ResponseBody
    public String insert(User user) {
        userService.save(user);
        return getAll();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(User user) {
        boolean save = userService.updateById(user);
        return getAll();
    }

}