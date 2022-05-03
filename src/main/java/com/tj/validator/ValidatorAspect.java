package com.tj.validator;

import com.tj.model.response.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 参数校验切面
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/2 22:39
 **/
@Aspect
@Component
public class ValidatorAspect {

    @Pointcut("@annotation(com.tj.validator.ParamValidate)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object paramValidate(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取返回值类型
        Class<?> returnType = getReturnType(joinPoint);
        if (returnType == Response.class) {
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {

                Response response = new Response();
                for (Object arg : joinPoint.getArgs()) {
                    if (arg == null) {
                        continue;
                    }

                    ParameterValidator.validate(arg, response);
                }

                if (!response.getSuccess()) {
                    return response;
                }
            }
        }

        return joinPoint.proceed();
    }

    private Class<?> getReturnType(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Method proxyMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method sourceMethod = joinPoint.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        return sourceMethod.getReturnType();
    }
}
