package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.UserRolesMapper;
import com.gemframework.model.entity.po.UserRoles;
import com.gemframework.service.UserRolesService;
import org.springframework.stereotype.Service;

@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {

}