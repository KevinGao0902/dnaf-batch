package com.dnaf.batch.core.job;

import com.dnaf.batch.core.leader.LeaderElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 只有主节点才执行的任务
 */
@Component
public class LeaderTask {
    @Autowired
    private LeaderElectionService leaderElectionService;

    @Scheduled(fixedDelay = 60000)
    public void doLeaderJob() {
        if (leaderElectionService.isMaster()) {
            System.out.println("我是主节点，进行分布式批处理逻辑分发…");
            // 业务批处理任务入口
        }
    }
}