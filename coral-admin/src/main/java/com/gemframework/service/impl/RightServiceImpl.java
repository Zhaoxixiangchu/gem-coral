package com.gemframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.mapper.RightMapper;
import com.gemframework.model.entity.po.Right;
import com.gemframework.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RightServiceImpl extends ServiceImpl<RightMapper, Right> implements RightService {

    @Autowired
    private RightMapper rightMapper;

}