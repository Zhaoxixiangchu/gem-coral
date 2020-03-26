/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.RoleDeptsMapper;
import com.gemframework.model.entity.po.RoleDepts;
import com.gemframework.model.entity.vo.RoleDeptsVo;
import com.gemframework.service.RoleDeptsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleDeptsServiceImpl extends ServiceImpl<RoleDeptsMapper, RoleDepts> implements RoleDeptsService {


    @Autowired
    RoleDeptsMapper roleDeptsMapper;

    @Override
    @Transactional
    public boolean save(RoleDeptsVo vo) {
        //先删除
        deleteByRoleId(vo.getRoleId());

        //重新保存
        if(StringUtils.isNotBlank(vo.getDeptIds())){
            List<RoleDepts> list = new ArrayList<>();
            List<Long> deptIds = Arrays.asList(vo.getDeptIds().split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            for(Long deptId : deptIds){
                RoleDepts entity = new RoleDepts();
                entity.setRoleId(vo.getRoleId());
                entity.setDeptId(deptId);
                list.add(entity);
            }
            this.saveBatch(list);
        }
        return true;
    }

    @Override
    public boolean deleteByRoleId(Long roleId) {
        List roleDeptIds = roleDeptsMapper.findIdsByRoleId(roleId);
        if(roleDeptIds!=null && !roleDeptIds.isEmpty()){
            roleDeptsMapper.deleteBatchIds(roleDeptIds);
        }
        return true;
    }
}