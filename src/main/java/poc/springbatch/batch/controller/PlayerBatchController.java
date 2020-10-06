package poc.springbatch.batch.controller;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poc.springbatch.batch.service.PlayerBatchService;

@RestController
@RequestMapping("/api/v1/player-batches")
class PlayerBatchController {

    private PlayerBatchService service;

    @Autowired
    PlayerBatchController(PlayerBatchService service) {
        this.service = service;
    }

    @GetMapping
    public BatchStatus runPlayerBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        return service.run();
    }

}
