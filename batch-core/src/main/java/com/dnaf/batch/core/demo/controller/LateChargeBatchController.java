package com.dnaf.batch.core.demo.controller;

import com.dnaf.batch.core.demo.batch.LateChargeBatchLauncher;
import com.dnaf.batch.core.demo.domain.LateChargeBatchParam;
import com.dnaf.batch.core.leader.LeaderElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * DEMO
 *
 * @author Kevin
 * @date 2025/12/01 18:55:80
 */
@RestController
@RequestMapping("/api/batch/csrLateCharge")
public class LateChargeBatchController {
    @Autowired
    private LeaderElectionService leaderElectionService;
    @Autowired
    private LateChargeBatchLauncher batchLauncher;

    @PostMapping("/run")
    public ResponseEntity<String> run(@RequestBody LateChargeBatchParam param) throws Exception {
        if (!leaderElectionService.isMaster()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("非主节点，拒绝执行");
        }
        batchLauncher.launch(param);
        return ResponseEntity.ok("OK");
    }
}