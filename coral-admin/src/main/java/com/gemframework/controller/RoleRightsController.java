package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.vo.RoleRightsVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.RoleRightsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/roleRights")
public class RoleRightsController extends BaseController{

    @Autowired
    private RoleRightsService roleRightsService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("roleRights:page")
    public BaseResultData page(PageInfo pageInfo, RoleRightsVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = roleRightsService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }


    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("roleRights:list")
    public BaseResultData list(RoleRightsVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = roleRightsService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("roleRights:save")
    public BaseResultData save(@Valid @RequestBody RoleRightsVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return BaseResultData.SUCCESS(roleRightsService.save(vo));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("roleRights:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) roleRightsService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                roleRightsService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}