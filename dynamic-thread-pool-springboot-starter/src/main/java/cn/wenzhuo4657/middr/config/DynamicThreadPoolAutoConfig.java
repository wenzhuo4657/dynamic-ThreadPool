package cn.wenzhuo4657.middr.config;

import cn.wenzhuo4657.middr.domain.DynamicThreadPoolService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className: DynamicThreadPoolAutoConfig
 * @author: wenzhuo4657
 * @date: 2024/5/30 17:51
 * @Version: 1.0
 * @description: 自动化配置
 */
@Configuration
public class DynamicThreadPoolAutoConfig {
    Logger logger= LoggerFactory.getLogger(DynamicThreadPoolAutoConfig.class);
    @Bean("dynamicThreadPollService" )
    public DynamicThreadPoolService dynamicThreadPoolService(ApplicationContext context, Map<String,ThreadPoolExecutor> threadPoolExecutorMap){
        String applicationName=context.getEnvironment().getProperty("spring.application.name");
        if (StringUtils.isBlank(applicationName)){
            applicationName="缺省的";
            logger.warn("动态线程池提示，无法找到应用名称");
        }
        logger.info("线程池信息，{}", JSON.toJSONString(threadPoolExecutorMap.keySet()));
        return  new DynamicThreadPoolService(applicationName,threadPoolExecutorMap);
    }




}