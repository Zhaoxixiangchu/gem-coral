package com.gemframework.impl;

import com.gemframework.UserService;
import com.gemframework.admin.modules.system.po.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
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
}
