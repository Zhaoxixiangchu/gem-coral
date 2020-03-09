package com.gemframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemframework.model.entity.po.User;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User 表数据库控制层接口
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("selectUserList")
    List<User> selectUserList();
}