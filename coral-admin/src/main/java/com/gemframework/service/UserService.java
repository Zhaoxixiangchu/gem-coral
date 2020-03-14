package com.gemframework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gemframework.model.entity.po.User;

import java.util.Set;

public interface UserService extends IService<User> {

    //根据用户名获取用户信息
    User getByUserName(String username);

    Set<String> getRoles(String username);

    Set<String> getRights(String username);
}