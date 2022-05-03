package com.tj.dal.entity;

import lombok.Data;

/**
 * 用户表实体类
 *
 * @author tangjie
 */
@Data
public class User {
    /**
     * 无意义主键
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

}