package com.gemframework.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.po.User;
import com.gemframework.model.entity.vo.RoleVo;
import com.gemframework.model.entity.vo.UserVo;
import com.gemframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    public BaseResultData page(PageInfo pageInfo) {
        Page page = userService.page(setOrderPage(pageInfo));
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 根据参数获取列表分页
     * @return
     */
    @GetMapping("/pageByParams")
    public BaseResultData pageByParams(PageInfo pageInfo, UserVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = userService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/list")
    public BaseResultData list() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort_number");
        List list = userService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public BaseResultData saveOrUpdate(UserVo vo) {
        User entity = GemBeanUtils.copyProperties(vo,User.class);
        return BaseResultData.SUCCESS(userService.saveOrUpdate(entity));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
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