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
 * @Title: Member
 * @Package: com.gemframework.model.entity.po
 * @Date: 2020-04-11 13:31:43
 * @Version: v1.0
 * @Description: 生成器配置实体对象
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@TableName("gem_sys_generator")
@Data
public class Generator extends BaseEntityPo {

    private String tabalePrefix;
    private String packageName;
    private String moduleName;
    private String authorName;
    private String authorEmail;
}