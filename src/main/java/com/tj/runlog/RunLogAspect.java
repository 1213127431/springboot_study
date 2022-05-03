package com.tj.runlog;

import com.tj.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 自定义日志打印切面
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/3 10:48
 **/
@Aspect
@Slf4j
@Component
public class RunLogAspect {

    @Pointcut("execution(* com.tj.controller..*.*(..))||execution(* com.tj.dal.repo..*.*(..))")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法名
        String methodName = methodSignature.getMethod().getName();
        // 获取类名
        String className = methodSignature.getMethod().getDeclaringClass().getName();
        // 获取完整的方法名
        String fullMethodName = String.format("%s.%s", className, methodName);

        // 记录方法入参
        Object[] args = joinPoint.getArgs();
        StringBuilder param = new StringBuilder();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                param.append("param[").append(i).append("] = ");
                if (args[i] == null) {
                    param.append("null");
                } else {
                    param.append(JsonUtil.toJson(args[i]));
                }

                if (i != args.length - 1) {
                    param.append("||");
                }
            }
        }

        // 记录方法开始时间
        long beginTime = System.currentTimeMillis();

        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            log.info("方法:[{}],入参:[{}],出参:[{}],耗时:[{}]",
                    fullMethodName, param.toString(), JsonUtil.toJson(result), System.currentTimeMillis() - beginTime);
        }

        return result;
    }
}
