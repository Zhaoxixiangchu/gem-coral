package com.gemframework.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gemframework.model.entity.po.User;
import com.gemframework.model.entity.vo.UserVo;

import java.util.List;
import java.util.Set;

public interface UserService extends IService<User> {

    UserVo save(UserVo vo);

    UserVo update(UserVo vo);

    //根据用户名获取用户信息
    List<UserVo> pageByParams(Page page, Wrapper wrapper);

    //根据用户名获取用户信息
    User getByUserName(String username);

    Set<String> getRoles(String username);

    Set<String> getRights(String username);
}