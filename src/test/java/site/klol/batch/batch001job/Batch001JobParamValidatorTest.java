package site.klol.batch.batch001job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import site.klol.batch.batch001job.constants.JobParamConstant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Batch001JobParamValidatorTest {
    JobParametersValidator jobParametersValidator = new Batch001JobParamValidator();

    @Test
    @DisplayName("성공 테스트: 수동 실행이면 유효성 검사 없이 실행")
    void 유효성_검사_테스트_01() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.IS_MANUAL_RUN, "Y")
            .toJobParameters();

        Assertions.assertDoesNotThrow(()-> jobParametersValidator.validate(jobParameters));
    }

    @Test
    @DisplayName("실패 테스트: REQUEST_TIME이 주어지지 않음")
    void 유효성_검사_테스트_02(){
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .toJobParameters();

        Assertions.assertThrows(JobParametersInvalidException.class,
            () -> jobParametersValidator.validate(jobParameters));
    }

    @Test
    @DisplayName("실패 테스트: REQUEST_DATE가 주어지지 않음.")
    void 유효성_검사_테스트_03(){
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_TIME, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
            .toJobParameters();

        Assertions.assertThrows(JobParametersInvalidException.class,
            () -> jobParametersValidator.validate(jobParameters));
    }

    @Test
    @DisplayName("성공 테스트")
    void 유효성_검사_테스트_04(){
        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .addString(JobParamConstant.REQUEST_TIME, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
            .toJobParameters();

        Assertions.assertDoesNotThrow(() -> jobParametersValidator.validate(jobParameters));
    }
}