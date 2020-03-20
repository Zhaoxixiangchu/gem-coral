package com.gemframework.controller;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.ZtreeEntity;
import com.gemframework.model.entity.po.Right;
import com.gemframework.model.entity.vo.RightVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.MenuType;
import com.gemframework.service.RightService;
import lombok.extern.slf4j.Slf4j;
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


    /***
     * 加载当前权限用户的部门树
     * @return
     */
    @GetMapping("/tree")
    public BaseResultData tree(){
        QueryWrapper queryWrapper = setSort();
        List<Right> list = rightService.list(queryWrapper);
        List<ZtreeEntity> ztreeEntities = initRootTree();
        for(Right entity:list){
            ZtreeEntity ztreeEntity = ZtreeEntity.builder()
                    .id(entity.getId())
                    .pid(entity.getPid())
                    .name(entity.getName())
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
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    public BaseResultData page(PageInfo pageInfo) {
        Page page = rightService.page(setOrderPage(pageInfo));
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/list")
    public BaseResultData list() {
        QueryWrapper queryWrapper = setSort();
        List list = rightService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/listByParams")
    public BaseResultData listByParams(RightVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = rightService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public BaseResultData saveOrUpdate(RightVo vo) {
        Right entity = GemBeanUtils.copyProperties(vo,Right.class);
        if(!rightService.saveOrUpdate(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public BaseResultData delete(Long id) {
        return BaseResultData.SUCCESS(rightService.removeById(id));
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
    public static List<RightVo> rightTree(List<RightVo> list){
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


    public static void main(String[] args) {
        Right r1 = new Right();
        r1.setId(1L);
        r1.setPid(0L);
        r1.setName("系统管理");
        r1.setFlag("sys");
        r1.setIcon("layui-icon-unlink");
        r1.setLevel(1);
        r1.setLink("");
        r1.setType(0);//菜单

        Right r2 = new Right();
        r2.setId(2L);
        r2.setPid(1L);
        r2.setName("权限管理");
        r2.setFlag("right");
        r2.setIcon("layui-icon-gift");
        r2.setLevel(2);
        r2.setLink("right/list");
        r2.setType(0);//菜单

        Right r3 = new Right();
        r3.setId(3L);
        r3.setPid(2L);
        r3.setName("添加权限");
        r3.setFlag("right:add");
        r3.setIcon("layui-icon-bluetooth");
        r3.setLevel(3);
        r3.setLink("right/list");
        r3.setType(1);//按钮

        Right r4 = new Right();
        r4.setId(5L);
        r4.setPid(0L);
        r4.setName("控制台");
        r4.setFlag("right:add");
        r4.setIcon("layui-icon-bluetooth");
        r4.setLevel(3);
        r4.setLink("home");
        r4.setType(1);//按钮

        List<Right> list = new ArrayList<>();
        list.add(r4);
        list.add(r1);
        list.add(r2);
        list.add(r3);
        List<RightVo> voList = GemBeanUtils.copyCollections(list,RightVo.class);
        log.info(JSONObject.toJSONString(rightTree(voList)));
    }

}