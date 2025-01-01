package cn.wenzhuo4657.middr.registry;

import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;

import java.util.Map;

/**
 * @className: IRedisRegistry
 * @author: wenzhuo4657
 * @date: 2024/5/30 19:40
 * @Version: 1.0
 * @description:
 */
public interface IRedisRegistry {
      /**
         *  des: 重导入线程池列表,仅仅重导入当前应用名称的线程池列表
         * */
    void reportThreadPool(Map<String, ThreadPoolConfigEntity> threadPoolEntities);


}