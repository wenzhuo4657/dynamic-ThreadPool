package cn.wenzhuo4657.middr.tigger.listener;

import cn.wenzhuo4657.middr.Application.IDynamicThreadPoolService;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import com.alibaba.fastjson.JSON;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @className: ThreadPoolConfigAdjustListener
 * @author: wenzhuo4657
 * @date: 2024/6/3 17:36
 * @Version: 1.0
 * @description: 动态线程池redis发布、订阅监听器。
 * 每当有一个消息放松到指定频道时，该监听器都能收到，也就是将注册中心的线程池配置（也可能是其他用户更新到redis的配置）同步到本地。
 * 当有其他用户更新线程池配置时，同步到本地。
 */
public class ThreadPoolConfigAdjustListener implements MessageListener<HashMap<String,ThreadPoolConfigEntity>> {
    private Logger logger = LoggerFactory.getLogger(ThreadPoolConfigAdjustListener.class);
    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRedisRegistry registry;

    public ThreadPoolConfigAdjustListener(IDynamicThreadPoolService dynamicThreadPoolService, IRedisRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

      /**
         *  des: 当有线程池发布到监听的频道时，每个使用该客户端的应用程序都会重新导入
         * */



    @Override
    public void onMessage(CharSequence channel, HashMap<String, ThreadPoolConfigEntity> msg) {
//        for ()
//            logger.info("动态线程池，调整线程池配置。线程池名称:{} 核心线程数:{} 最大线程数:{}", threadPoolConfigEntity.getThreadPoolName(), threadPoolConfigEntity.getPoolSize(), threadPoolConfigEntity.getMaximumPoolSize());
//        dynamicThreadPoolService.updateThreadPoolConfig(threadPoolConfigEntity);//更新到本地
//
//        // 更新后上报最新数据
//        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
//        registry.reportThreadPool(threadPoolConfigEntities);//1，刷新redis中池化列表
    }
}