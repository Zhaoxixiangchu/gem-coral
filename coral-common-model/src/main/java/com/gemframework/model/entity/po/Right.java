/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
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
@TableName("gem_sys_right")
@Data
public class Right extends BaseEntityPo {

    //父级ID
    private Long pid;
    //名称
    private String name;
    //标识组 多个以逗号隔开
    private String flags;
    //图标
    private String icon;
    //链接
    private String link;
    //类型 0 菜单 1 按钮 2 权限
    private Integer type;
    //级别
    private Integer level;
    //位置 0 左侧 1 顶部 2 底部
    private Integer position;

}