package site.klol.batch001.job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;
import site.klol.batch001.job.constants.JobParamConstant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class Batch001JobParamValidator implements JobParametersValidator {

    private static final String TIME_PATTERN = "^([01]\\d|2[0-3]):([0-5]\\d)$";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_PATTERN = "\\d+";
    private static final String DATE_FORMAT = "yyyyMMdd";

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        if (jobParameters == null) {
            throw new JobParametersInvalidException("Job parameters must not be null");
        }

        validateReuqestDate(jobParameters);
        validateReuqestTime(jobParameters);
        validateIsManualRun(jobParameters);
    }

    private void validateIsManualRun(JobParameters jobParameters) throws JobParametersInvalidException {
        String isManualRun = jobParameters.getString(JobParamConstant.IS_MANUAL_RUN);

        if (isManualRun != null && isManualRun.equalsIgnoreCase("y")) {
            throw new JobParametersInvalidException("Invalid isManualRun: " + isManualRun);
        }
    }

    private void validateReuqestTime(JobParameters jobParameters) throws JobParametersInvalidException {
        // TIME format "HH:MM"
        String requestTime = jobParameters.getString(JobParamConstant.REQUEST_TIME);
        if (requestTime == null || requestTime.length() != 5 || !requestTime.matches(TIME_PATTERN)) {
            throw new JobParametersInvalidException("Invalid requestTime parameter: " + requestTime);
        }

        try {
            LocalTime.parse(requestTime, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException("Invalid requestTime parameter: " + requestTime);
        }
    }

    private static void validateReuqestDate(JobParameters jobParameters) throws JobParametersInvalidException {
        // DATE format "yyyyMMdd"
        String requestDate = jobParameters.getString(JobParamConstant.REQUEST_DATE);
        if (requestDate == null || requestDate.length() != 8 || !requestDate.matches(DATE_PATTERN)) {
            throw new JobParametersInvalidException("Invalid requestDate parameter: " + requestDate);
        }

        try {
            LocalDate.parse(requestDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException("Invalid requestDate parameter: " + requestDate);
        }
    }
}

