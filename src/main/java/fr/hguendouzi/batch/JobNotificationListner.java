package fr.hguendouzi.batch;

import fr.hguendouzi.entity.User;
import fr.hguendouzi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hguendouzi
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JobNotificationListner extends JobExecutionListenerSupport {

    private final UserRepository repository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("the job is started ");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            Optional<User> result = repository.findByEmail("hicham@test.fr");
            result.ifPresentOrElse(user -> log.info("FIRST USER FOUND IN DATA BASE {}.", user.getEmail()),
                    () -> log.info(" SORRY NO USER FOUND "));
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("batch failed");
        }

    }

}
