package com.tj.thread;

import com.tj.core.utils.concurrent.threadpool.CommonThreadPoolExecutors;
import com.tj.log.ParamNoPrint;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池管理
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/4 20:15
 **/
@Component
public class ThreadPoolManager {

    private final ExecutorService executorService =
            CommonThreadPoolExecutors.newHalfMaxThreadPool("commonExecutor", 100, 10000);

    @ParamNoPrint
    public ExecutorService getExecutorService() {
        return executorService;
    }
}
