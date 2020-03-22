package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.StatusValidator;
import com.gemframework.model.entity.vo.RoleDeptsVo;
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
@RequestMapping("/roleDepts")
public class RoleDeptsController extends BaseController{

    @Autowired
    private RoleDeptsService roleDeptsService;



    /**
     * 获取列表分页
     * @return
     */
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