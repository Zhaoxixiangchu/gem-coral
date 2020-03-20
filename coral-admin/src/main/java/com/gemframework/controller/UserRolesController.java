package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.po.UserRoles;
import com.gemframework.model.entity.vo.UserRolesVo;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.service.UserRolesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/userRoles")
public class UserRolesController extends BaseController{

    @Autowired
    private UserRolesService userRolesService;


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    public BaseResultData page(PageInfo pageInfo) {
        Page page = userRolesService.page(setOrderPage(pageInfo));
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }


    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/pageByParams")
    public BaseResultData pageByParams(PageInfo pageInfo, UserRolesVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = userRolesService.page(setOrderPage(pageInfo),queryWrapper);
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
        List list = userRolesService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/listByParams")
    public BaseResultData listByParams(UserRolesVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = userRolesService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加或编辑
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public BaseResultData saveOrUpdate(UserRolesVo vo) {
        UserRoles entity = GemBeanUtils.copyProperties(vo, UserRoles.class);
        if(!userRolesService.saveOrUpdate(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }


    /**
     * 添加
     * @return
     */
    @PostMapping("/save")
    public BaseResultData save(UserRolesVo vo) {
        List<UserRoles> list = new ArrayList<>();
        List<Long> Ids = Arrays.asList(vo.getRoleIds().split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
        for(Long id : Ids){
            UserRoles entity = new UserRoles();
            entity.setUserId(vo.getUserId());
            entity.setRoleId(id);
            list.add(entity);
        }
        return BaseResultData.SUCCESS(userRolesService.saveBatch(list));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) userRolesService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                userRolesService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }

}