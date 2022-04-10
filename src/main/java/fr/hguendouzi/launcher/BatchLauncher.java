package fr.hguendouzi.launcher;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

/**
 * @author hguendouzi
 */
@Component
@RequiredArgsConstructor
public class BatchLauncher {

    private final JobLauncher jobLauncher;
    private final Job job;

    public BatchStatus runBatch() throws Exception {
        JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        return jobExecution.getStatus();
    }
}
