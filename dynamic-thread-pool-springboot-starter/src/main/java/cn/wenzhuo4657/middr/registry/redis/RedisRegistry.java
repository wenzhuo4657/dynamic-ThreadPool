package cn.wenzhuo4657.middr.registry.redis;

import cn.wenzhuo4657.middr.config.DynamicThreadPoolAutoConfig;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.domain.model.valobj.RegistryEnumVO;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    public void reportThreadPool(Map<String, ThreadPoolConfigEntity> threadPoolEntities) {
        RMap<String, ThreadPoolConfigEntity> map = redissonClient.getMap(DynamicThreadPoolAutoConfig.getApplicationConfig());

        for (String  key:map.keySet()){
            ThreadPoolConfigEntity threadPoolConfigEntity = threadPoolEntities.get(key);
            if (Objects.isNull(threadPoolConfigEntity)){
                continue;
            }
            map.putIfExists(key,threadPoolConfigEntity);
        }
    }

    @Override
    public RLock getRLockByName(String name) {
        return redissonClient.getFairLock(name);
    }
}