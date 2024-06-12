package cn.wenzhuo4657.kir;

import cn.wenzhuo4657.kir.Application;
import cn.wenzhuo4657.middr.domain.model.enity.ThreadPoolConfigEntity;
import cn.wenzhuo4657.middr.registry.IRedisRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className: cn.wenzhuo4657.kir.Apitest
 * @author: wenzhuo4657
 * @date: 2024/5/30 17:52
 * @Version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Apitest {

    @Resource
    private RTopic dynamicThreadPoolRedisTopic;


    @Test
    @Autowired
    public void test() throws InterruptedException {
        ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity("dynamic-thread-pool-test-app", "threadPoolExecutor01");
        threadPoolConfigEntity.setPoolSize(100);
        threadPoolConfigEntity.setMaximumPoolSize(100);
        dynamicThreadPoolRedisTopic.publish(threadPoolConfigEntity);
        new CountDownLatch(1).await();
    }

}