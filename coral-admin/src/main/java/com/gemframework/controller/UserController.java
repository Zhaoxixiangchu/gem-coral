package com.gemframework.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.PasswordValidator;
import com.gemframework.model.common.validator.StatusValidator;
import com.gemframework.model.common.validator.UpdateValidator;
import com.gemframework.model.entity.vo.UserVo;
import com.gemframework.service.UserService;
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
    public BaseResultData save(@RequestBody UserVo vo) {
        GemValidate(vo, StatusValidator.class);
        return BaseResultData.SUCCESS(userService.save(vo));
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("user:update")
    public BaseResultData update(@RequestBody UserVo vo) {
        GemValidate(vo, UpdateValidator.class);
        return BaseResultData.SUCCESS(userService.update(vo));
    }

    /**
     * 重置密码
     * @return
     */
    @PostMapping("/password")
    @RequiresPermissions("user:password")
    public BaseResultData password(@RequestBody UserVo vo) {
        GemValidate(vo, PasswordValidator.class);
        return BaseResultData.SUCCESS(userService.update(vo));
    }

    /**
     * 修改用户状态
     * @return
     */
    @PostMapping("/status")
    @RequiresPermissions("user:status")
    public BaseResultData status(@RequestBody UserVo vo) {
        GemValidate(vo, StatusValidator.class);
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