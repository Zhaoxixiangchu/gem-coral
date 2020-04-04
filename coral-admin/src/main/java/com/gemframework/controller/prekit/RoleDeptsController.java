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
import com.gemframework.annotation.Log;
import com.gemframework.constant.GemModules;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.StatusValidator;
import com.gemframework.model.entity.vo.RoleDeptsVo;
import com.gemframework.model.enums.OperateType;
import com.gemframework.service.RoleDeptsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(GemModules.PreKit.PATH_RBAC+"/roleDepts")
public class RoleDeptsController extends BaseController {

    private static final String moduleName = "角色部门关联信息";

    @Autowired
    private RoleDeptsService roleDeptsService;



    /**
     * 获取列表分页
     * @return
     */
    @Log(type = OperateType.NORMAL,value = "分页查询"+moduleName)
    @GetMapping("/page")
    @RequiresPermissions("roleDepts:page")
    public BaseResultData page(PageInfo pageInfo, RoleDeptsVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = roleDeptsService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表
     * @return
     */
    @Log(type = OperateType.NORMAL,value = "列表查询"+moduleName)
    @GetMapping("/list")
    @RequiresPermissions("roleDepts:list")
    public BaseResultData list(RoleDeptsVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = roleDeptsService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("roleDepts:save")
    public BaseResultData save(@RequestBody RoleDeptsVo vo) {
        GemValidate(vo, StatusValidator.class);
        return BaseResultData.SUCCESS(roleDeptsService.save(vo));
    }


    /**
     * 删除
     * @return
     */
    @Log(type = OperateType.ALTER,value = "删除"+moduleName)
    @PostMapping("/delete")
    @RequiresPermissions("roleDepts:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) roleDeptsService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                roleDeptsService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}