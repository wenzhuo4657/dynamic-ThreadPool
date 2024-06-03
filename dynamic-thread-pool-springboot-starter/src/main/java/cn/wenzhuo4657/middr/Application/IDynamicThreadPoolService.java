package cn.wenzhuo4657.middr.Application;

import cn.wenzhuo4657.middr.domain.DynamicThreadPoolService;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

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