/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemframework.model.entity.po.Right;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: RightMapper
 * @Package: com.gemframework.mapper
 * @Date: 2020-03-09 15:31:33
 * @Version: v1.0
 * @Description: Mapper接口
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@Repository
public interface RightMapper extends BaseMapper<Right> {

    List<Right> findRightsByRoleAndType(Map<Object, Object> map);
}