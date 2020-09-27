package poc.springbatch.batch.processing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (isJobCompleted(jobExecution)) {
            log.info("Job have been finished!");
        }
    }

    private boolean isJobCompleted(JobExecution jobExecution) {
        return jobExecution.getStatus() == BatchStatus.COMPLETED;
    }

}
