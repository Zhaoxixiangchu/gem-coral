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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseResultData page(PageInfo pageInfo) {
        Page page = roleService.page(setOrderPage(pageInfo));
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/pageByParams")
    public BaseResultData pageByParams(PageInfo pageInfo,RoleVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = roleService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/list")
    public BaseResultData list() {
        QueryWrapper queryWrapper = setSort();
        List list = roleService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/listByParams")
    public BaseResultData listByParams(RoleVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = roleService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public BaseResultData saveOrUpdate(RoleVo vo) {
        BaseResultData baseResultData = BaseResultData.builder().build();
        Role entity = GemBeanUtils.copyProperties(vo, Role.class);
        if(!roleService.saveOrUpdate(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public BaseResultData delete(Long id,String ids) {
        roleService.delete(id,ids);
        return BaseResultData.SUCCESS();
    }

}