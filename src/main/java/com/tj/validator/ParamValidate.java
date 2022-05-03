package com.tj.validator;

import java.lang.annotation.*;

/**
 * 自定义参数校验注解
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/2 22:19
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ParamValidate {

}
