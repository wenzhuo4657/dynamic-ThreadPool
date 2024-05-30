package cn.wenzhuo4657.middr.Application;

import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @className: IDynamicThreadPoolService
 * @author: wenzhuo4657
 * @date: 2024/5/30 19:19
 * @Version: 1.0
 * @description: 动态线程池服务
 */
public interface IDynamicThreadPoolService {

      /**
         *  des: 查询线程池配置列表
         * */
    List<ThreadPoolConfigEntity> queryThreadPoolList();


      /**
         *  des: 根据线程池名称查找线程池配置
         * */

    ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName);

      /**
         *  des: 更新线程池
       *  1，如果当前应用名称不一致，无法更新
       *  2，如果不存在该线程池无法更新，也就是是说，该更新不能执行创建
         * */

    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);
}