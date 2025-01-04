package cn.wenzhuo4657.middr.config;

import cn.wenzhuo4657.middr.Application.IDynamicThreadPoolService;
import cn.wenzhuo4657.middr.domain.DynamicThreadPoolService;
import cn.wenzhuo4657.middr.domain.model.Exception.DynamicThreadPoolAppException;
import cn.wenzhuo4657.middr.domain.model.Exception.ResponseCode;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.domain.model.valobj.RegistryEnumVO;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import cn.wenzhuo4657.middr.registry.redis.RedisRegistry;
import cn.wenzhuo4657.middr.tigger.job.ThreadPoolDataReportJob;
import cn.wenzhuo4657.middr.tigger.listener.ThreadPoolConfigAdjustListener;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className: DynamicThreadPoolAutoConfig
 * @author: wenzhuo4657
 * @date: 2024/5/30 17:51
 * @Version: 1.0
 * @description: 自动化配置
 */
@Configuration
//@EnableConfigurationProperties(DynamicThreadPoolAutoProperties.class)
@EnableScheduling
public class DynamicThreadPoolAutoConfig {
    Logger logger= LoggerFactory.getLogger(DynamicThreadPoolAutoConfig.class);

    private static String applicationName;

    public static String   getApplicationConfig(){
        return  RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey() + "_" + applicationName;
    }

    @Bean("dynamicThreadPollService" )
    public DynamicThreadPoolService dynamicThreadPoolService(ApplicationContext context, Map<String,ThreadPoolExecutor> threadPoolExecutorMap,RedissonClient redissonClient){
        applicationName=context.getEnvironment().getProperty("spring.application.name");
        if (StringUtils.isBlank(applicationName)){
            logger.error("动态线程池提示，无法找到应用名称");
            throw new DynamicThreadPoolAppException(ResponseCode.ApplicationName_ERROR.getCode(),ResponseCode.ApplicationName_ERROR.getInfo());
        }

        Set<String> threadPoolKeys = threadPoolExecutorMap.keySet();

        // 尝试获取缓存数据，设置本地线程池配置

        HashMap<String,ThreadPoolConfigEntity> threadPoolConfigEntityList = redissonClient.
                <HashMap<String,ThreadPoolConfigEntity>>
                getBucket(getApplicationConfig()).get();

        if (null != threadPoolConfigEntityList){

            for (String threadPoolKey : threadPoolKeys) {
                ThreadPoolConfigEntity threadPoolConfigEntity = threadPoolConfigEntityList.get(threadPoolKey);
                threadPoolConfigEntity.setCorePoolSize(threadPoolConfigEntity.getCorePoolSize());
                threadPoolConfigEntity.setMaximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize());
            }
        }

        logger.info("线程池信息，{}", JSON.toJSONString(threadPoolExecutorMap.keySet()));
        return  new DynamicThreadPoolService(applicationName,threadPoolExecutorMap);
    }

    public static String getApplicationName() {
        return applicationName;
    }

    //    @Bean("redissonClientDynamicThreadPool")
//    public RedissonClient redissonClient(DynamicThreadPoolAutoProperties properties) {
//        Config config = new Config();
//        config.setCodec(JsonJacksonCodec.INSTANCE);
//        config.useSingleServer()
//                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
//                .setPassword(properties.getPassword())
//                .setConnectionPoolSize(properties.getPoolSize())
//                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
//                .setIdleConnectionTimeout(properties.getIdleTimeout())
//                .setConnectTimeout(properties.getConnectTimeout())
//                .setRetryAttempts(properties.getRetryAttempts())
//                .setRetryInterval(properties.getRetryInterval())
//                .setPingConnectionInterval(properties.getPingInterval())
//                .setKeepAlive(properties.isKeepAlive())
//                .setDatabase(properties.getDatabaseIndex())
//        ;
//
//        RedissonClient redissonClient = Redisson.create(config);
//
//        logger.info("动态线程池，注册器（redis）链接初始化完成。{} {} {}", properties.getHost(), properties.getPoolSize(), !redissonClient.isShutdown());
//
//        return redissonClient;
//    }
    @Bean
    public IRedisRegistry redisRegistry(RedissonClient redissonClientDynamicThreadPool) {
        return new RedisRegistry(redissonClientDynamicThreadPool);
    }



    @Bean
    public ThreadPoolDataReportJob threadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRedisRegistry registry) {
        return new ThreadPoolDataReportJob(dynamicThreadPoolService, registry);
    }

    @Bean
    public ThreadPoolConfigAdjustListener threadPoolConfigAdjustListener(IDynamicThreadPoolService dynamicThreadPoolService, IRedisRegistry registry) {
        return new ThreadPoolConfigAdjustListener(dynamicThreadPoolService, registry);
    }

      /**
         *  des: 将监听器注入redisson
         * */
    @Bean( "dynamicThreadPoolRedisTopic")
    public RTopic threadPoolConfigAdjustListener(RedissonClient redissonClient, ThreadPoolConfigAdjustListener threadPoolConfigAdjustListener) {
        RTopic topic = redissonClient.getTopic(RegistryEnumVO.DYNAMIC_THREAD_POOL_REDIS_TOPIC.getKey() + "_" + applicationName);
        topic.addListener(HashMap.class, threadPoolConfigAdjustListener);
        return topic;
    }

}