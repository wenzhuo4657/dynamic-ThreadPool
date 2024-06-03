package cn.wenzhuo4657.middr.registry;

import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @className: IRedisRegistry
 * @author: wenzhuo4657
 * @date: 2024/5/30 19:40
 * @Version: 1.0
 * @description:
 */
public interface IRedisRegistry {
      /**
         *  des: 重导入线程池列表
         * */
    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolEntities);

      /**
         *  des: 重导入线程池配置
         * */

    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);
}