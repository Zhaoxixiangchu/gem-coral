package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.vo.UserRolesVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.UserRolesService;
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
@RequestMapping("/userRoles")
public class UserRolesController extends BaseController{

    @Autowired
    private UserRolesService userRolesService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("userRoles:page")
    public BaseResultData page(PageInfo pageInfo, UserRolesVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = userRolesService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }


    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("userRoles:list")
    public BaseResultData list(UserRolesVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = userRolesService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("userRoles:save")
    public BaseResultData save(@Valid @RequestBody UserRolesVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return BaseResultData.SUCCESS(userRolesService.save(vo));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("userRoles:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) userRolesService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                userRolesService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}