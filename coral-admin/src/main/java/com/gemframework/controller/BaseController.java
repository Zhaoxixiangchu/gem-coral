package com.gemframework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.common.utils.GemStringUtils;
import com.gemframework.model.common.BaseEntityVo;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.enums.SortType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BaseController {

    @NotNull
    static Page setOrderPage(PageInfo pageInfo) {
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

    static QueryWrapper makeQueryMaps(BaseEntityVo vo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Object> map = GemBeanUtils.ObjectToMap(vo);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = GemStringUtils.humpToLine(entry.getKey());
            Object paramVal = entry.getValue();
            log.info("key= " + fieldName + " and value= "+paramVal);
            if(paramVal != null && StringUtils.isNotBlank(String.valueOf(paramVal))){
                queryWrapper.like(fieldName,paramVal);
            }
        }
        return queryWrapper;
    }

}