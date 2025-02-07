package site.klol.batch001.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;
import site.klol.batch001.job.constants.JobParamConstant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Validates job parameters for the batch001 job.
 * Ensures that date, time, and manual run parameters are properly formatted and valid.
 */
@Slf4j
@Component
public class Batch001JobParamValidator implements JobParametersValidator {

    private static final String TIME_PATTERN = "^([01]\\d|2[0-3]):([0-5]\\d)$";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "yyyyMMdd";

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        log.debug("Validating job parameters: {}", jobParameters);
        
        if (jobParameters == null) {
            throw new JobParametersInvalidException("Job parameters must not be null");
        }

        if (!isManualRun(jobParameters)) {
            validateRequestDate(jobParameters);
            validateRequestTime(jobParameters);
        }

        log.info("Job parameters validation successful");
    }

    private boolean isManualRun(JobParameters jobParameters) throws JobParametersInvalidException {
        String isManualRun = Optional.ofNullable(jobParameters.getString(JobParamConstant.IS_MANUAL_RUN))
                .orElse("N");

        if (!"Y".equalsIgnoreCase(isManualRun) && !"N".equalsIgnoreCase(isManualRun)) {
            throw new JobParametersInvalidException(
                String.format("Invalid isManualRun parameter: %s. Must be 'Y' or 'N'", isManualRun));
        }

        return "Y".equalsIgnoreCase(isManualRun);
    }

    private void validateRequestTime(JobParameters jobParameters) throws JobParametersInvalidException {
        String requestTime = jobParameters.getString(JobParamConstant.REQUEST_TIME);
        
        if (requestTime == null || requestTime.isEmpty()) {
            throw new JobParametersInvalidException("requestTime parameter is required");
        }
        
        if (requestTime.length() != 5 || !requestTime.matches(TIME_PATTERN)) {
            throw new JobParametersInvalidException(
                String.format("Invalid time format: %s. Must match pattern HH:mm", requestTime));
        }

        try {
            LocalTime.parse(requestTime, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException(
                String.format("Failed to parse time: %s. Error: %s", requestTime, e.getMessage()));
        }
    }

    private void validateRequestDate(JobParameters jobParameters) throws JobParametersInvalidException {
        String requestDate = jobParameters.getString(JobParamConstant.REQUEST_DATE);
        
        if (requestDate == null || requestDate.isEmpty()) {
            throw new JobParametersInvalidException("requestDate parameter is required");
        }

        if (requestDate.length() != 8) {
            throw new JobParametersInvalidException(
                String.format("Invalid date format: %s. Must be in format yyyyMMdd", requestDate));
        }

        try {
            LocalDate.parse(requestDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException(
                String.format("Failed to parse date: %s. Error: %s", requestDate, e.getMessage()));
        }
    }
}
