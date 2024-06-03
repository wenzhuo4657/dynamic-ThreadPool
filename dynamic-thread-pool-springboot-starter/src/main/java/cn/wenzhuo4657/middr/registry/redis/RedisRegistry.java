package cn.wenzhuo4657.middr.registry.redis;

import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.domain.model.valobj.RegistryEnumVO;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.List;

/**
 * @className: RedisRegistry
 * @author: wenzhuo4657
 * @date: 2024/5/30 19:40
 * @Version: 1.0
 * @description: 将池化配置等更新到注册中心
 */
public class RedisRegistry implements IRedisRegistry {
    private final RedissonClient redissonClient;
    public RedisRegistry(RedissonClient redissonClient) {
        this.redissonClient=redissonClient;
    }

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolEntities) {
        RList<Object> list = redissonClient.getList(RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey());
        list.delete();
        list.addAll(threadPoolEntities);

    }

    @Override
    public void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity) {
        String cacheKey=RegistryEnumVO.THREAD_POOL_CONFIG_PARAMETER_LIST_KEY.getKey()+"_"+ threadPoolConfigEntity.getAppName() + "_" + threadPoolConfigEntity.getThreadPoolName();
        RBucket<ThreadPoolConfigEntity> bucket=redissonClient.getBucket(cacheKey);
        bucket.set(threadPoolConfigEntity, Duration.ofDays(30));
    }
}