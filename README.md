# DNAF Batch Distributed Processing Platform

> 分布式高可用批处理架构，Spring Boot 4，Gateway-Nacos选主，自动主节点续租/切换
> 可观测性完善，支持 Grafana + Prometheus + ELK 日志分析

## 模块结构

```
dnaf-batch/
├── batch-core      # 批处理主服务，自动选举主节点，暴露REST接口
├── batch-gateway   # 微服务入口网关（Leader选主任务只转发主节点）
├── batch-admin     # 运维监控(Spring Boot Admin Server)
├── pom.xml         # Maven聚合根
```

## 主节点自动选举/续租/掉线切换

- 核心代码见 `LeaderElectionService.java` 和 `LeaderTask.java`
- 推荐生产用 Nacos/Consul/Atomix 实现严格一致性主节点选举

## 调度架构建议

- XXL-JOB Admin 独立部署，仅调度 Gateway 入口
- 批任务自动转发到主节点（Leader）
- 其它分布式任务逻辑由主节点分发/处理

## 可观测性/日志

- 所有服务支持 Prometheus 监控采集，Grafana建图报警
- 日志可通过 ELK (Logstash/Fluentbit/Beats) 推送到ES，Kibana检索
- 推荐统一标准日志格式

## 快速启动

1. 启动 Nacos, Redis, Oracle, Prometheus, Grafana, ELK
2. batch-admin > batch-gateway > batch-core
3. 配置 XXL-JOB Admin 执行器调用 Gateway 入口路径
4. Prometheus 采集各服务 `/actuator/prometheus`
5. Grafana导入DNAF自定义仪表盘，Kibana搜集日志

## 推荐依赖版本

- Spring Boot 4.0.0
- Nacos 2.x
- Sentinel 2.x
- Alibaba Cloud SDK 2025.0.0.0
- Oracle JDBC 21.x
- Druid 1.2.27
- Micrometer 1.12.x（Prometheus registry）

---

如需详细 Prometheus、ELK 配置样例、K8s/Compose或更多分布式选主代码，欢迎随时补充需求！