package cn.wenzhuo4657.middr.registry.redis;

import cn.wenzhuo4657.middr.config.DynamicThreadPoolAutoConfig;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.domain.model.valobj.RegistryEnumVO;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.util.Map;

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
//        RList<Object> list = redissonClient.getList(RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey());
//
//        for (Object obj:list){
//            if (obj instanceof ThreadPoolConfigEntity){
//                String appName = ((ThreadPoolConfigEntity) obj).getAppName();
//                if (appName!=null&& DynamicThreadPoolAutoConfig.getApplicationName().equals(appName)){
//                    list.remove(obj);
//                }
//            }
//        }
//        list.addAll(threadPoolEntities);
    }

}