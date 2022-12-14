package cc.sven.springboottemplate.config;

import cc.sven.springboottemplate.exception.AsyncExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class AsyncConfiguration implements AsyncConfigurer {

    // First Async Executor
    @Override
    public SimpleAsyncTaskExecutor getAsyncExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("Async-Executor");
        return executor;
    }

    // Additional Async Executor(s)
    @Bean("AsyncEventExecutor")
    @Qualifier(value = "AsyncEventExecutor")
    public ThreadPoolTaskExecutor getAsyncPersistExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setThreadNamePrefix("asy-Evnt-Exec");
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
