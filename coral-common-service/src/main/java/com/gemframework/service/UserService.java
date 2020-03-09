package com.gemframework.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gemframework.model.entity.po.User;

import java.util.Set;

public interface UserService extends IService<User> {

    Set getRoles(String username);

    Set getPermissions(String username);

    User getByUsername(String username);

    Page<User> selectUserPage(Page<User> page);
}

