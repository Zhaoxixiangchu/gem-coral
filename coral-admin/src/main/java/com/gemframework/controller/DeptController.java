package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.ZtreeEntity;
import com.gemframework.model.entity.po.Dept;
import com.gemframework.model.entity.vo.DeptVo;
import com.gemframework.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController{

    @Autowired
    private DeptService deptService;


    /***
     * 加载当前权限用户的部门树
     * @return
     */
    @GetMapping("/tree")
    public BaseResultData tree(){
        QueryWrapper queryWrapper = setSort();
        List<Dept> list = deptService.list(queryWrapper);
        List<ZtreeEntity> ztreeEntities = initRootTree();
        for(Dept entity:list){
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
        Page page = deptService.page(setOrderPage(pageInfo));
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/pageByParams")
    public BaseResultData pageByParams(PageInfo pageInfo,DeptVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = deptService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/list")
    public BaseResultData list() {
        Dept dept = new Dept();
        dept.setId(0L);
        dept.setPid(-1L);
        dept.setName("集团总部");
        dept.setFullname("集团总部");

        QueryWrapper queryWrapper = setSort();
        List<Dept> list = deptService.list(queryWrapper);
        list.add(dept);
        return BaseResultData.SUCCESS(list);
    }


    /**
     * 获取列表
     * @return
     */
    @GetMapping("/listByParams")
    public BaseResultData listByParams(DeptVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = deptService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public BaseResultData saveOrUpdate(DeptVo vo) {
        Dept entity = GemBeanUtils.copyProperties(vo, Dept.class);
        return BaseResultData.SUCCESS(deptService.saveOrUpdate(entity));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) deptService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                deptService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}