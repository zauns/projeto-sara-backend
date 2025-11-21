package sara.projeto.saraEmprega.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    private final int nCpus = Runtime.getRuntime().availableProcessors();

    @Bean(name = "ioBound")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(nCpus*2);
        executor.setMaxPoolSize(nCpus*4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "cpuBound")
    public Executor cpuBoundExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(nCpus);
        executor.setMaxPoolSize(nCpus);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("CPU-");
        executor.initialize();
        return executor;
    }
}
