package com.tj.validator;

import com.tj.model.response.Response;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数校验实现
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/2 22:28
 **/
public class ParameterValidator {

    private static final String ERROR_MSG_SEPARATOR = ",";

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T obj, Response response) {
        String msg = validate(obj);
        if (!StringUtils.isEmpty(msg)) {
            response.setSuccess(false);
            response.setCode("1");
            response.setMessage(msg);
        }
    }

    private static <T> String validate(T obj) {
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(obj);
        if (CollectionUtils.isEmpty(validate)) {
            return null;
        }

        return validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(ERROR_MSG_SEPARATOR));
    }
}
