package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.entity.po.User;
import com.gemframework.model.entity.vo.UserVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("test")
public class TestController {

    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping("user/add")
    public BaseResultData add(@Valid @RequestBody UserVo vo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        GemBeanUtils.copyProperties(vo,user);
        userService.save(user);
        return BaseResultData.SUCCESS();
    }

    @ResponseBody
    @RequestMapping("user/update")
    public String userUpdate() {
        User user = new User();
        user.setId(5L);
        user.setRealname("李四");
        userService.updateById(user);
        return "succ";
    }
    @ResponseBody
    @RequestMapping("user/get")
    public User userGet(Long id) {
        User user = userService.getById(id);
        return user;
    }

    @ResponseBody
    @RequestMapping("user/list")
    public List<User> userList() {
        List<User> users = userService.list();
        return users;
    }

    @ResponseBody
    @RequestMapping("user/page")
    public BaseResultData page(Page page) {
        page = userService.page(page);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    @ResponseBody
    @RequestMapping("user/pageByParam")
    public BaseResultData pageByParam(Page page,UserVo userVo) {
        QueryWrapper<User> wp = new QueryWrapper<User>();
        wp.eq("username","zhangsan");
        page = userService.page(page, wp);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    @ResponseBody
    @RequestMapping("user/delete")
    public BaseResultData delete(Page page,UserVo userVo) {
        UpdateWrapper<User> wp = new UpdateWrapper<User>();
        wp.eq("username","zhangsan");
        userService.remove(wp);
        return page(page);
    }

}