package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.po.Role;
import com.gemframework.model.entity.vo.RoleVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{

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
    public BaseResultData save(@Valid @RequestBody RoleVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        BaseResultData baseResultData = BaseResultData.builder().build();
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
    public BaseResultData update(@Valid @RequestBody RoleVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        BaseResultData baseResultData = BaseResultData.builder().build();
        Role entity = GemBeanUtils.copyProperties(vo, Role.class);
        if(!roleService.updateById(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("role:delete")
    public BaseResultData delete(Long id,String ids) {
        roleService.delete(id,ids);
        return BaseResultData.SUCCESS();
    }

}