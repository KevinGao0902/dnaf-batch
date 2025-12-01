package com.dnaf.batch.core.demo.batch;

import com.dnaf.batch.core.demo.domain.LateChargeBatchParam;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
public class LateChargeBatchLauncher {
    private final JobLauncher jobLauncher;
    private final Job lateChargeJob;

    public LateChargeBatchLauncher(JobLauncher jobLauncher, Job lateChargeJob) {
        this.jobLauncher = jobLauncher;
        this.lateChargeJob = lateChargeJob;
    }

    public void launch(LateChargeBatchParam param) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobId", java.util.UUID.randomUUID().toString())
                .addString("batchDate", param.getBatchDate())
                .toJobParameters();
        jobLauncher.run(lateChargeJob, jobParameters);
    }
}