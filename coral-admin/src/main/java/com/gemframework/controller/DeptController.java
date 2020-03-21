package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.ZtreeEntity;
import com.gemframework.model.entity.po.Dept;
import com.gemframework.model.entity.vo.DeptVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.DeptService;
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
@RequestMapping("/dept")
public class DeptController extends BaseController{

    @Autowired
    private DeptService deptService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("dept:page")
    public BaseResultData page(PageInfo pageInfo,DeptVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = deptService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("dept:list")
    public BaseResultData list(DeptVo vo) {
        Dept dept = new Dept();
        dept.setId(0L);
        dept.setPid(-1L);
        dept.setName("集团总部");
        dept.setFullname("集团总部");
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = deptService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("dept:save")
    public BaseResultData save(@Valid @RequestBody DeptVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Dept entity = GemBeanUtils.copyProperties(vo, Dept.class);
        if(!deptService.save(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("dept:update")
    public BaseResultData update(@Valid @RequestBody DeptVo vo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return BaseResultData.ERROR(ErrorCode.PARAM_EXCEPTION.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Dept entity = GemBeanUtils.copyProperties(vo, Dept.class);
        if(!deptService.updateById(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 删除 & 批量刪除
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("dept:delete")
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


    /***
     * 获取部门树
     * @return
     */
    @GetMapping("/tree")
    @RequiresPermissions("dept:tree")
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
}