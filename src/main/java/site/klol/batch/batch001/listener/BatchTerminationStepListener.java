package site.klol.batch.batch001.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.common.exception.NoSkipException;

@Component
public class BatchTerminationStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LoggerContext.getLogger().info("Step 시작: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LoggerContext.getLogger().info("Step 종료. 읽은 수: {}, 쓴 수: {}, 건너뛴 수: {}",
            stepExecution.getReadCount(),
            stepExecution.getWriteCount(),
            stepExecution.getSkipCount());

        if (stepExecution.getFailureExceptions().stream()
            .anyMatch(e -> e instanceof NoSkipException)) {
            LoggerContext.getLogger().error("치명적 오류 발생으로 인한 Step 실패");
            return ExitStatus.FAILED;
        }

        return ExitStatus.COMPLETED;
    }
}