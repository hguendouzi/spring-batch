package fr.hguendouzi.config;

import fr.hguendouzi.batch.JobNotificationListner;
import fr.hguendouzi.batch.UserItemProcess;
import fr.hguendouzi.batch.UserItemWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author hguendouzi
 */
@SpringBatchTest
@ContextConfiguration(classes = {BatchConfig.class, DataSourceConfig.class})
@TestPropertySource(properties = {"constants.header.names=lastName,firstName,email,address"})
class BatchConfigTest {
    private static final String STEP_NAME = "importUser";
    private static final String Line_1 = "le sage,thomas,lesage.thomas@info.fr,24 rue de lattre de tassigny";
    private static final String results = "src/test/resources/results.csv";
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    @Qualifier("configDataSource")
    private DataSource dataSource;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @MockBean
    private UserItemWriter itemWriter;
    @MockBean
    private UserItemProcess itemProcess;
    @MockBean
    private JobNotificationListner jobNotificationListner;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void testSuccessfulReading() throws Exception {
        FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
        reader.setLineMapper(new PassThroughLineMapper());
        reader.setResource(new FileSystemResource(results));
        // open, provide "mock" ExecutionContext
        reader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
        int count = 0;
        String line;
        while ((line = reader.read()) != null) {
            assertThat(line).isEqualTo(Line_1);
            count++;
        }
        assertThat(count).isEqualTo(1);


    }


    @Test
    public void shouldBeSuccessfullyJob() throws Exception {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", results);
        paramsBuilder.addString("names", "lastName,firstName,email,address");
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(paramsBuilder.toJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");
        assertThat(actualJobInstance.getJobName()).isEqualTo("importUserJob");

    }

}
