package com.dnaf.batch.core.te.contrller;


import com.dnaf.batch.core.te.entity.BatchJob;
import com.dnaf.batch.core.te.service.BatchJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/batch/jobs")
@RequiredArgsConstructor
public class BatchJobController {

    private final BatchJobService batchJobService;

    /**
     * 创建任务
     */
    @PostMapping
    public int createJob(@RequestBody BatchJob batchJob) {
        int result = 0;
        try {
            result = batchJobService.insert(batchJob);
            log.info("保存结果: {}, 任务ID: {}", result, batchJob.getJobId());
            return result;
        } catch (Exception e) {
            log.error("保存失败: {}", e.getMessage(), e);
        }
        return result;
    }


}
