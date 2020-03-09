package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.UserMapper;
import com.gemframework.model.entity.po.User;
import com.gemframework.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Set getRoles(String username) {
        return null;
    }

    @Override
    public Set getPermissions(String username) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }


    public Page<User> selectUserPage(Page<User> page) {
        page.setRecords(baseMapper.selectUserList());
        return page;
    }


}
