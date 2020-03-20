package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.po.RoleRights;
import com.gemframework.model.entity.vo.RoleRightsVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.RoleRightsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/roleRights")
public class RoleRightsController extends BaseController{

    @Autowired
    private RoleRightsService roleRightsService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    public BaseResultData page(PageInfo pageInfo) {
        Page page = roleRightsService.page(setOrderPage(pageInfo));
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/pageByParams")
    public BaseResultData pageByParams(PageInfo pageInfo, RoleRightsVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = roleRightsService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    public BaseResultData list() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort_number");
        List list = roleRightsService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/listByParams")
    public BaseResultData listByParams(RoleRightsVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = roleRightsService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public BaseResultData saveOrUpdate(RoleRightsVo vo) {
        RoleRights entity = GemBeanUtils.copyProperties(vo,RoleRights.class);
        if(!roleRightsService.saveOrUpdate(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("/save")
    public BaseResultData save(RoleRightsVo vo) {
        return BaseResultData.SUCCESS(roleRightsService.save(vo));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) roleRightsService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                roleRightsService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}