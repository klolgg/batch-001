package site.klol.batch.batch001;

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

/**
 * Configuration class for the main batch job that processes match data.
 * This job consists of three steps:
 * 1. Fetching new match IDs
 * 2. Retrieving match details
 * 3. Processing match history
 */
@Configuration
@RequiredArgsConstructor
public class Batch001JobConfig {
    private static final String LOGGER_NAME = "site.klol.batch.batch001Job";
    private static final String JOB_NAME = "batch001Job";
    private static final Logger log = LoggerFactory.getLogger(LOGGER_NAME);
    private final JobRepository jobRepository;

    @Bean
    public Job batch001Job(
            Step newMatchIdStep,
            Step newMatchDetailsStep,
            Step matchHistoryProcessingStep,
            JobParametersValidator batch001JobParamValidator) {
        
        log.info("Initializing {} job", JOB_NAME);

        LoggerContext.setLogger(log);
        
        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .validator(batch001JobParamValidator)
            .start(newMatchIdStep)
            .next(newMatchDetailsStep)
            .next(matchHistoryProcessingStep)
            .build();
    }
}
