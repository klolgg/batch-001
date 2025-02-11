package site.klol.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.klol.batch.batch001.constants.JobParamConstant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Profile({"!cli","prd","schd"})
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job batch001Job;
    private final Job batch002Job;

    @Async
    @Scheduled(cron = "0 */30 * * * *")
    public void runBatch001Job()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .addString(JobParamConstant.REQUEST_TIME, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
            .toJobParameters();

        jobLauncher.run(batch001Job, jobParameters);
    }

    @Async
    @Scheduled(cron = "0 */30 * * * *")
    public void runBatch002Job()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .addString(JobParamConstant.REQUEST_TIME, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
            .toJobParameters();

        jobLauncher.run(batch002Job, jobParameters);
    }



}
