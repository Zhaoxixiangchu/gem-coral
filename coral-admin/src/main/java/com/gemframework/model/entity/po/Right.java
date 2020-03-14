package com.gemframework.model.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;


/**
 * @Title: Right
 * @Package: com.gemframework.model.entity.po
 * @Date: 2020-03-13 14:22:15
 * @Version: v1.0
 * @Description: 权限信息
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@TableName("gem_right")
@Data
public class Right extends BaseEntityPo {

    private Long pid;
    private String name;
    private String flag;
    private String icon;
    private String link;
    private Integer type;
    private Integer level;

}