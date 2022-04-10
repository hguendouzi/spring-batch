package fr.hguendouzi.launcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author hguendouzi
 */
@ExtendWith(SpringExtension.class)
class BatchLauncherTest {
    @InjectMocks
    BatchLauncher launchJob;
    @Mock
    JobLauncher jobLauncher;
    @Mock
    Job job;

    @Test
    public void when_batch_is_excuted_success_should_be_return_status_complet() throws Exception {
        JobExecution jobExecution = new JobExecution(1l);
        jobExecution.setStatus(BatchStatus.COMPLETED);
        when(jobLauncher.run(Mockito.any(Job.class), Mockito.any(JobParameters.class))).thenReturn(jobExecution);
        BatchStatus status = launchJob.runBatch();
        assertThat(status).isEqualTo(BatchStatus.COMPLETED);

    }

    @Test
    public void when_batch_is_faild_should_be_return_status_failed() throws Exception {
        JobExecution jobExecution = new JobExecution(1l);
        jobExecution.setStatus(BatchStatus.FAILED);
        when(jobLauncher.run(Mockito.any(Job.class), Mockito.any(JobParameters.class))).thenReturn(jobExecution);
        BatchStatus status = launchJob.runBatch();
        assertThat(status).isEqualTo(BatchStatus.FAILED);

    }

}