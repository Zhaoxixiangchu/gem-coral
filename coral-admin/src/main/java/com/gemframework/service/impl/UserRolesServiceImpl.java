package com.gemframework.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.UserRolesMapper;
import com.gemframework.model.entity.po.UserRoles;
import com.gemframework.model.entity.vo.UserRolesVo;
import com.gemframework.service.UserRolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {

    @Autowired
    UserRolesMapper userRolesMapper;

    @Override
    public List<UserRoles> findByUserId(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List<UserRoles> list = userRolesMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<Long> findIdsByUserId(Long userId) {
        List userRolesIds = userRolesMapper.findIdsByUserId(userId);
        return userRolesIds;
    }

    @Override
    public boolean deleteByUserId(Long userId) {
        List userRolesIds = userRolesMapper.findIdsByUserId(userId);
        if(userRolesIds!=null && !userRolesIds.isEmpty()){
            userRolesMapper.deleteBatchIds(userRolesIds);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean save(UserRolesVo vo) {
        //2、 保存用户关联角色信息
        //2.1、先根据用户ID查询userRoles进行删除
        deleteByUserId(vo.getUserId());
        //2.2、再保存用户关联角色信息
        List<UserRoles> list = new ArrayList<>();
        List<Long> roleIds = Arrays.asList(vo.getRoleIds().split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
        for(Long roleId : roleIds){
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(vo.getUserId());
            userRoles.setRoleId(roleId);
            list.add(userRoles);
        }
        log.info("UserRolesList=============="+ JSON.toJSONString(list));
        super.saveBatch(list);
        return true;
    }
}