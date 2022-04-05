package za.co.standardbank.assessment1.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import za.co.standardbank.assessment1.method.ProcessScheduledAction;
import za.co.standardbank.assessment1.persistence.repositories.ScheduledActionRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class RetryActionScheduler implements SchedulingConfigurer {

    private final ScheduledActionRepository scheduledActionRepository;

    private final ProcessScheduledAction processScheduledAction;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        scheduledActionRepository.findByActiveIsTrue()
                .forEach(action -> {
                    log.debug("Creating scheduler for {}", action);
                    taskRegistrar.setScheduler(taskExecutor());
                    taskRegistrar.addTriggerTask(
                            () -> processScheduledAction.execute(action),
                            context -> {
                                CronTrigger crontrigger = new CronTrigger(action.getFrequency());
                                return crontrigger.nextExecutionTime(context);
                            }
                    );
                });

    }
}
