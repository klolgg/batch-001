package site.klol.batch001.job.schedular;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.klol.batch001.job.constants.JobParamConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Profile({"!cli","prd","schd"})
@Slf4j
public class Batch001Schedular {
    private final JobLauncher jobLauncher;
    private final Job batch001Job;

    @Scheduled(cron = "0 */30 * * * *")
    public void runJob()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .addString(JobParamConstant.REQUEST_TIME, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
            .toJobParameters();

        log.info("Starting job at {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        jobLauncher.run(batch001Job, jobParameters);
    }

}
