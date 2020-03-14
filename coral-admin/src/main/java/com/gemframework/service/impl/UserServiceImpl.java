package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.UserMapper;
import com.gemframework.model.entity.po.User;
import com.gemframework.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUserName(String username) {
        return null;
    }

    @Override
    public Set<String> getRoles(String username) {
        return null;
    }
    @Override
    public Set<String> getRights(String username) {
        return null;
    }
}