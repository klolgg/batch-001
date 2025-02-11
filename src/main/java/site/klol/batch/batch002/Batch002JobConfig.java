package site.klol.batch.batch002;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.klol.batch.common.LoggerContext;

@Configuration
@RequiredArgsConstructor
public class Batch002JobConfig {
    private static final String LOGGER_NAME = "site.klol.batch.batch002Job";
    private static final String JOB_NAME = "batch002Job";
    private static final Logger log = LoggerFactory.getLogger(LOGGER_NAME);
    private final JobRepository jobRepository;


    @Bean
    public Job batch002Job(
        Step renewSummonerStep,
        JobParametersValidator batch001JobParamValidator
        ) {

        log.info("Initializing {} job", JOB_NAME);

        LoggerContext.setLogger(log);

        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .validator(batch001JobParamValidator)
            .start(renewSummonerStep)
            .build();
    }
}
