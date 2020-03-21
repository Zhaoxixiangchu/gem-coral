package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.common.utils.GemStringUtils;
import com.gemframework.model.common.BaseEntityVo;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.ZtreeEntity;
import com.gemframework.model.entity.po.User;
import com.gemframework.model.enums.SortType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class BaseController {

    @NotNull
    public static Page setOrderPage(PageInfo pageInfo) {
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("sort_number").setAsc(SortType.asc.getCode() == 0);
        orderItem.setColumn(pageInfo.getSort()).setAsc(pageInfo.getOrder().asc.getCode() == 0);

        List orders = new ArrayList();
        orders.add(orderItem);
        Page page = new Page();
        page.setOrders(orders);
        page.setSize(pageInfo.getLimit());
        page.setCurrent(pageInfo.getPage());
        return page;
    }

    public static QueryWrapper setSort() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort_number");
        return queryWrapper;
    }

    public static QueryWrapper makeQueryMaps(BaseEntityVo vo) {
        QueryWrapper queryWrapper = setSort();
        queryWrapper.eq("deleted",0);
        Map<String,Object> map = GemBeanUtils.ObjectToMap(vo);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = GemStringUtils.humpToLine(entry.getKey());
            Object paramVal = entry.getValue();
            log.info("key= " + fieldName + " and value= "+paramVal);
            queryWrapper.like(paramVal != null && StringUtils.isNotBlank(String.valueOf(paramVal)),fieldName,paramVal);
        }
        return queryWrapper;
    }


    /**
     * 将list格式是权限数据，转化成tree格式的权限数据。
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public static List<ZtreeEntity> toTree(List<ZtreeEntity> treeNodes) {
        List<ZtreeEntity> trees = new ArrayList<ZtreeEntity>();
        for (ZtreeEntity treeNode : treeNodes) {
            if (-1 == (treeNode.getPid())) {
                trees.add(treeNode);
            }
            for (ZtreeEntity it : treeNodes) {
                if (it.getPid() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<ZtreeEntity>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }


    public static List<ZtreeEntity> initRootTree() {
        List<ZtreeEntity> ztreeEntities = new ArrayList<>();
        ZtreeEntity ztreeEntity = ZtreeEntity.builder()
                .id(0L)
                .pid(-1L)
                .name("顶层目录|全选/全消")
                .title("顶层目录|全选/全消")
                .level(0)
                .open(true)
                .nocheck(true)
                .build();
        ztreeEntities.add(ztreeEntity);

        return ztreeEntities;
    }


    protected User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

}