package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.RightMapper;
import com.gemframework.mapper.RoleMapper;
import com.gemframework.model.entity.po.Right;
import com.gemframework.model.entity.po.Role;
import com.gemframework.service.RightService;
import com.gemframework.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RightMapper rightMapper;

}