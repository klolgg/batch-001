package site.klol.batch001.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the main batch job that processes match data.
 * This job consists of three steps:
 * 1. Fetching new match IDs
 * 2. Retrieving match details
 * 3. Processing match history
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class Batch001JobConfig {
    private static final String JOB_NAME = "batch001Job";
    private final JobRepository jobRepository;

    @Bean
    public Job batch001Job(
            Step newMatchIdStep,
            Step newMatchDetailsStep,
            Step matchHistoryProcessingStep,
            JobParametersValidator batch001JobParamValidator) {
        
        log.info("Initializing {} job", JOB_NAME);
        
        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .validator(batch001JobParamValidator)
            .start(newMatchIdStep)
            .next(newMatchDetailsStep)
            .next(matchHistoryProcessingStep)
            .build();
    }
}
