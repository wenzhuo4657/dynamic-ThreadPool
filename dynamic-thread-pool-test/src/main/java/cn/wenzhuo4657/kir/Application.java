package cn.wenzhuo4657.kir;

import cn.wenzhuo4657.middr.config.MyWsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * @className: Application
 * @author: wenzhuo4657
 * @date: 2024/5/30 18:04
 * @Version: 1.0
 * @description:
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


}