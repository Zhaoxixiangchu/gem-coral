package com.gemframework.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.vo.UserVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.UserService;
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
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 根据参数获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("user:page")
    public BaseResultData page(PageInfo pageInfo, UserVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = setOrderPage(pageInfo);
        List<UserVo> userVos = userService.pageByParams(page,queryWrapper);
        return BaseResultData.SUCCESS(userVos,page.getTotal());
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public BaseResultData list(UserVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = userService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("user:save")
    public BaseResultData save(@Valid @RequestBody UserVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return BaseResultData.SUCCESS(userService.save(vo));
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("user:update")
    public BaseResultData update(@Valid @RequestBody UserVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return BaseResultData.SUCCESS(userService.update(vo));
    }

    /**
     * 删除 & 批量删除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("user:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) userService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                userService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}