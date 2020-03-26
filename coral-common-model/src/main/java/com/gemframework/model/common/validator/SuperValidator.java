/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.common.validator;

import javax.validation.GroupSequence;

/**
 * @Title: SuperValidator
 * @Package: com.gemframework.model.common.validator
 * @Date: 2020-03-22 11:11:52
 * @Version: v1.0
 * @Description: 定义校验顺序，一旦有校验失败就中断
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@GroupSequence({SaveValidator.class, UpdateValidator.class})
public interface SuperValidator {

}
