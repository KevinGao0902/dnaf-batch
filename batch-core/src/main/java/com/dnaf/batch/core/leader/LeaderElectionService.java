package com.dnaf.batch.core.leader;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LeaderElectionService {
    private static final String MASTER_KEY = "batch:master:node";
    private static final long MASTER_LEASE = 30;
    private static final long RENEW_INTERVAL = 10;

    private final StringRedisTemplate redisTemplate;

    private final String instanceId = UUID.randomUUID().toString();
    private volatile boolean isMaster = false;

    public LeaderElectionService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void startLeaderElection() {
        tryElectMaster();
    }

    @Scheduled(fixedDelay = RENEW_INTERVAL * 1000)
    public void leaderRenewalLoop() {
        String currentMaster = redisTemplate.opsForValue().get(MASTER_KEY);
        if (instanceId.equals(currentMaster)) {
            redisTemplate.expire(MASTER_KEY, MASTER_LEASE, TimeUnit.SECONDS);
            isMaster = true;
        } else if (currentMaster == null) {
            tryElectMaster();
        } else {
            isMaster = false;
        }
    }

    public synchronized void tryElectMaster() {
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(MASTER_KEY, instanceId, MASTER_LEASE, TimeUnit.SECONDS);
        isMaster = Boolean.TRUE.equals(acquired);
    }

    public boolean isMaster() { return isMaster; }
    public String getInstanceId() { return instanceId; }
}