/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.controller.prekit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.constant.GemModules;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.StatusValidator;
import com.gemframework.model.common.validator.UpdateValidator;
import com.gemframework.model.entity.po.Role;
import com.gemframework.model.entity.vo.RoleVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(GemModules.PreKit.PATH_SYSTEM+"/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("role:page")
    public BaseResultData page(PageInfo pageInfo,RoleVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = roleService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("role:list")
    public BaseResultData list(RoleVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = roleService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("role:save")
    public BaseResultData save(@RequestBody RoleVo vo) {
        GemValidate(vo, StatusValidator.class);
        Role entity = GemBeanUtils.copyProperties(vo, Role.class);
        if(!roleService.save(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("role:update")
    public BaseResultData update(@RequestBody RoleVo vo) {
        GemValidate(vo, UpdateValidator.class);
        Role entity = GemBeanUtils.copyProperties(vo, Role.class);
        if(!roleService.updateById(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 删除 & 批量删除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("role:delete")
    public BaseResultData delete(Long id,String ids) {
        roleService.delete(id,ids);
        return BaseResultData.SUCCESS();
    }

}