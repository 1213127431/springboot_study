package com.tj.dal.repo;

import com.tj.dal.entity.User;
import com.tj.log.ParamPrint;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    @ParamPrint
    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);
}