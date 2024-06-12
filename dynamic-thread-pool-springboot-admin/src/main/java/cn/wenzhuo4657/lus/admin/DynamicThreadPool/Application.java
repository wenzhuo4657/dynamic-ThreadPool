package cn.wenzhuo4657.lus.admin.DynamicThreadPool;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: cn.wenzhuo4657.lus.admin.DynamicThreadPool.Application
 * @author: wenzhuo4657
 * @date: 2024/6/3 19:12
 * @Version: 1.0
 * @description:
 */
@SpringBootApplication
@Configuration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Configuration
    @EnableConfigurationProperties(RedisClientConfigProperties.class)
    public static class RedisClientConfig {

        @Bean("redissonClient")
        public RedissonClient redissonClient(ConfigurableApplicationContext applicationContext, RedisClientConfigProperties properties) {
            Config config = new Config();
            config.setCodec(JsonJacksonCodec.INSTANCE);
            config.useSingleServer()
                    .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                    .setConnectionPoolSize(properties.getPoolSize())
                    .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                    .setIdleConnectionTimeout(properties.getIdleTimeout())
                    .setConnectTimeout(properties.getConnectTimeout())
                    .setRetryAttempts(properties.getRetryAttempts())
                    .setRetryInterval(properties.getRetryInterval())
                    .setPingConnectionInterval(properties.getPingInterval())
                    .setKeepAlive(properties.isKeepAlive())
            ;

            return Redisson.create(config);
        }

    }

    @Data
    @ConfigurationProperties(prefix = "redis.sdk.config", ignoreInvalidFields = true)
    public static class RedisClientConfigProperties {
        /**
         * host:ip
         */
        private String host;
        /**
         * 端口
         */
        private int port;
        /**
         * 账密
         */
        private String password;
        /**
         * 设置连接池的大小，默认为64
         */
        private int poolSize = 64;
        /**
         * 设置连接池的最小空闲连接数，默认为10
         */
        private int minIdleSize = 10;
        /**
         * 设置连接的最大空闲时间（单位：毫秒），超过该时间的空闲连接将被关闭，默认为10000
         */
        private int idleTimeout = 10000;
        /**
         * 设置连接超时时间（单位：毫秒），默认为10000
         */
        private int connectTimeout = 10000;
        /**
         * 设置连接重试次数，默认为3
         */
        private int retryAttempts = 3;
        /**
         * 设置连接重试的间隔时间（单位：毫秒），默认为1000
         */
        private int retryInterval = 1000;
        /**
         * 设置定期检查连接是否可用的时间间隔（单位：毫秒），默认为0，表示不进行定期检查
         */
        private int pingInterval = 0;
        /**
         * 设置是否保持长连接，默认为true
         */
        private boolean keepAlive = true;
    }

}