package com.gemframework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.RightMapper;
import com.gemframework.model.entity.po.Right;
import com.gemframework.model.entity.po.Role;
import com.gemframework.model.entity.po.RoleRights;
import com.gemframework.service.RightService;
import com.gemframework.service.RoleRightsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RightServiceImpl extends ServiceImpl<RightMapper, Right> implements RightService {


    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleRightsService roleRightsService;

    @Override
    public Set<String> findRightsByRoles(Set<Role> roles) {
        //用户权限列表
        Set<String> rightsSet = new HashSet<>();
        if(roles != null && !roles.isEmpty()){
            for(Role role:roles){
                if(role!=null && role.getId()!= null){
                    QueryWrapper<RoleRights> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("role_id",role.getId());
                    List<RoleRights> roleRights = roleRightsService.list(queryWrapper);
                    if(roleRights!=null && !roleRights.isEmpty()){
                        for(RoleRights roleRight:roleRights){
                            if(roleRight!=null && roleRight.getRightId()!=null){
                                Right right = rightService.getById(roleRight.getRightId());
                                if(right!=null && StringUtils.isNotBlank(right.getFlag())){
                                    rightsSet.add(right.getFlag());
                                }
                            }
                        }
                    }
                }
            }
        }
        return rightsSet;
    }


    @Override
    public List<Right> findRightsByRolesAndType(Set<Role> roles,  Map<Object,Object> map) {
        //用户权限列表
        List<Right> rightsList = new ArrayList<>();
        if(roles != null && !roles.isEmpty()){
            for(Role role:roles){
                map.put("role_id",role.getId());
                rightsList = rightMapper.findRightsByRoleAndType(map);
//                if(role!=null && role.getId()!= null){
//                    queryWrapper.eq("role_id",role.getId());
//                    List<RoleRights> roleRights = roleRightsService.list(queryWrapper);
//                    if(roleRights!=null && !roleRights.isEmpty()){
//                        for(RoleRights roleRight:roleRights){
//                            if(roleRight!=null && roleRight.getRightId()!=null){
//                                Right right = rightService.getById(roleRight.getRightId());
//                                if(right!=null){
//                                    rightsList.add(right);
//                                }
//                            }
//                        }
//                    }
                }
            }
        return rightsList;
    }
}