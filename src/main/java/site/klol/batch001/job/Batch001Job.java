package site.klol.batch001.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Batch001Job {

    @Bean
    public Job batch001Job(JobRepository jobRepository) {
        return new JobBuilder("batch001Job", jobRepository)
            .start(getNewMatchIdList())
            .next(updateNewMatchList())
            .validator(validateParams())
            .build();
    }

    private JobParametersValidator validateParams() {
        // job param 검증
        // 날짜,
        return null;
    }

    private Step updateNewMatchList() {
        return null;
    }

    private Step getNewMatchIdList() {
        return null;
    }
}
