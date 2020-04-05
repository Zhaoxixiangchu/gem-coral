/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.mapper.UserMapper;
import com.gemframework.model.entity.po.Role;
import com.gemframework.model.entity.po.User;
import com.gemframework.model.entity.po.UserRoles;
import com.gemframework.model.entity.vo.RoleVo;
import com.gemframework.model.entity.vo.UserVo;
import com.gemframework.service.RoleService;
import com.gemframework.service.UserRolesService;
import com.gemframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRolesService userRolesService;


    @Override
    public UserVo save(UserVo vo) {
        return null;
    }

    @Override
    public UserVo update(UserVo vo) {
        return null;
    }

    @Override
    public List<UserVo> pageByParams(Page page, Wrapper wrapper) {
        page = super.page(page,wrapper);
        List<User> users = page.getRecords();
        List<UserVo> userVos = GemBeanUtils.copyCollections(users,UserVo.class);
        for(UserVo userVo:userVos){
            //查询userRoles
            List<RoleVo> roles = new ArrayList<>();
            List<UserRoles> userRolesList = userRolesService.findByUserId(userVo.getId());
            for(UserRoles userRoles:userRolesList){
                Role role = roleService.getById(userRoles.getRoleId());
                roles.add(GemBeanUtils.copyProperties(role,RoleVo.class));
            }
            userVo.setRoles(roles);
            //回显时密码不显示
            userVo.setPassword("");
        }
        return userVos;
    }

    @Override
    public User getByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getById(Long id) {
        return super.getById(id);
    }

    @Override
    public boolean exits(User entity) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",entity.getUsername())
                .or()
                .eq("phone",entity.getPhone())
                .or()
                .eq("email",entity.getEmail());
        //编辑
        if(entity.getId() != null && entity.getId() !=0){
            queryWrapper.and(wrapper -> wrapper.ne("id",entity.getId()));
        }
        if(count(queryWrapper)>0){
            return true;
        }
        return false;
    }
}