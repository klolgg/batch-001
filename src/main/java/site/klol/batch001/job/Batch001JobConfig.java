package site.klol.batch001.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    30분마다 실행되게
    30분이면 솔랭만
 */
@Configuration
@RequiredArgsConstructor
public class Batch001JobConfig {
    private static final String JOB_NAME = "batch001";
    private final JobRepository jobRepository;

    @Bean
    public Job batch001Job(Step newMatchIdStep,
                           Step newMatchDetailsStep,
                           JobParametersValidator batch001JobParamValidator) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(newMatchIdStep)
            .next(newMatchDetailsStep)
            .validator(batch001JobParamValidator)
            .build();
    }
}
