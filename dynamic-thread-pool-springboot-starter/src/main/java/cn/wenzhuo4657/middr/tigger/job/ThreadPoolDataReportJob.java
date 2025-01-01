package cn.wenzhuo4657.middr.tigger.job;

import cn.wenzhuo4657.middr.Application.IDynamicThreadPoolService;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * @className: ThreadPoolDataReportJob
 * @author: wenzhuo4657
 * @date: 2024/6/3 17:09
 * @Version: 1.0
 * @description: 线程池上报任务,定时将本地配置更新到注册中心
 */
public class ThreadPoolDataReportJob {
    private final Logger logger = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private  final IRedisRegistry redisRegistry;

    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRedisRegistry redisRegistry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.redisRegistry = redisRegistry;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void print(){
        Map<String, ThreadPoolConfigEntity> list=dynamicThreadPoolService.queryThreadPoolList();
        redisRegistry.reportThreadPool(list);
    }
}