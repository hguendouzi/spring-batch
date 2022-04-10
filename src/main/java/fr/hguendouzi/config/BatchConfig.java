package fr.hguendouzi.config;

import fr.hguendouzi.batch.JobNotificationListner;
import fr.hguendouzi.batch.UserItemProcess;
import fr.hguendouzi.batch.UserItemWriter;
import fr.hguendouzi.dto.UserDTO;
import fr.hguendouzi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author hguendouzi
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private static final String JOB_NAME = "importUserJob";
    private static final String STEP_NAME = "importUser";
    private static final String FILE_NAME = "results.csv";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserItemWriter itemWriter;
    private final UserItemProcess itemProcess;

    @Value("${constants.header.names}")
    private String[] names;


    @Bean
    public FlatFileItemReader<UserDTO> reader() {
        return new FlatFileItemReaderBuilder<UserDTO>()
                .name("personItemReader")
                .resource(new ClassPathResource(FILE_NAME))
                .delimited()
                .names(names)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(UserDTO.class);
                }})
                .build();
    }


    @Bean
    public Job importUserJob(JobNotificationListner listener, Step step1) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get(STEP_NAME)
                .<UserDTO, User>chunk(10)
                .reader(reader())
                .processor(itemProcess)
                .writer(itemWriter)
                .build();
    }


}
