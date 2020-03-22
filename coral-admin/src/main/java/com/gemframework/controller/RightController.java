package com.gemframework.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.ZtreeEntity;
import com.gemframework.model.common.validator.StatusValidator;
import com.gemframework.model.common.validator.UpdateValidator;
import com.gemframework.model.entity.po.Right;
import com.gemframework.model.entity.vo.RightVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.MenuType;
import com.gemframework.service.RightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: RightController
 * @Package: com.gemframework.controller
 * @Date: 2020-03-15 13:34:48
 * @Version: v1.0
 * @Description: 权限信息控制器
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping("/right")
public class RightController extends BaseController{

    @Autowired
    private RightService rightService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("right:page")
    public BaseResultData page(PageInfo pageInfo, RightVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = rightService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("right:list")
    public BaseResultData list(RightVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = rightService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("right:save")
    public BaseResultData save(@RequestBody RightVo vo) {
        GemValidate(vo, StatusValidator.class);
        Right entity = GemBeanUtils.copyProperties(vo,Right.class);
        if(!rightService.save(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 编辑
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("right:update")
    public BaseResultData update(@RequestBody RightVo vo) {
        GemValidate(vo, UpdateValidator.class);
        Right entity = GemBeanUtils.copyProperties(vo,Right.class);
        if(!rightService.updateById(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 删除 & 批量删除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("right:delete")
    public BaseResultData delete(Long id) {
        return BaseResultData.SUCCESS(rightService.removeById(id));
    }



    /***
     * 获取权限数据树
     * @return
     */
    @GetMapping("/tree")
    @RequiresPermissions("right:tree")
    public BaseResultData tree(){
        QueryWrapper queryWrapper = setSort();
        List<Right> list = rightService.list(queryWrapper);
        List<ZtreeEntity> ztreeEntities = initRootTree();
        for(Right entity:list){
            ZtreeEntity ztreeEntity = ZtreeEntity.builder()
                    .id(entity.getId())
                    .pid(entity.getPid())
                    .name(entity.getName()+" "+entity.getFlag())
                    .title(entity.getName())
                    .level(entity.getLevel())
                    .open(true)
                    .nocheck(false)
                    .build();
            ztreeEntities.add(ztreeEntity);
        }
        return BaseResultData.SUCCESS(toTree(ztreeEntities));
    }

    /**
     * 获取左侧菜单
     * @return
     */
//    @RequiresPermissions("right:add")
//    @PermitAll
    @RequestMapping("/leftSidebar")
    @ResponseBody
    public BaseResultData leftSidebar() {
        //判断用户角色，如果是超级管理员，返回所有
        QueryWrapper queryWrapper = setSort();
        queryWrapper.eq("type", MenuType.MENU.getCode());
        List<Right> list = rightService.list(queryWrapper);
        List<RightVo> voList = GemBeanUtils.copyCollections(list,RightVo.class);
        //TODO: 如果不是超级管理员，根据角色查询菜单权限

        return BaseResultData.SUCCESS(rightTree(voList));
    }


    /**
     * @Title: 将list格式是权限数据，转化成tree格式的权限数据。
     * @Param: [vos]
     * @Retrun: java.util.List<com.gemframework.model.vo.MenuVo>
     * @Description:
     * @Date: 2019/12/15 13:24
     */
    private static List<RightVo> rightTree(List<RightVo> list){
        List<RightVo> rightTree = new ArrayList<>();
        for (RightVo parent : list) {
            if (0 == (parent.getPid())) {
                rightTree.add(parent);
            }
            for (RightVo child : list) {
                if (child.getPid() == parent.getId()) {
                    if (parent.getChild() == null) {
                        parent.setChild(new ArrayList<>());
                    }
                    parent.getChild().add(child);
                }
            }
        }
        return rightTree;
    }
}