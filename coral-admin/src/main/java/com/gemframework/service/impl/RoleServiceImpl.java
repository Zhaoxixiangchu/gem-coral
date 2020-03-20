package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.RoleMapper;
import com.gemframework.model.entity.po.Role;
import com.gemframework.service.RoleDeptsService;
import com.gemframework.service.RoleRightsService;
import com.gemframework.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleDeptsService roleDeptsService;

    @Autowired
    private RoleRightsService roleRightsService;

    @Transactional
    public boolean delete(Long id, String ids) {
        if(id!=null){
            //刪除角色，顺便删除角色部门关联关系、角色权限关联关系
            if(removeById(id)){
                roleDeptsService.deleteByRoleId(id);
                roleRightsService.deleteByRoleId(id);
            }
        }

        //批量删除角色，遍历删除角色部门关联关系、角色权限关联关系
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                if(removeByIds(listIds)){
                    for(Long roleId:listIds){
                        roleDeptsService.deleteByRoleId(roleId);
                        roleRightsService.deleteByRoleId(roleId);
                    }
                }
            }
        }
        return true;
    }
}