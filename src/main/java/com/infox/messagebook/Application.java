package com.infox.messagebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;

/**
 * @author Ronnie
 */

//@SpringBootApplication(scanBasePackages = {"infox.itmanager6.boot","infox.itmanager6.web.controller"})

//@SpringBootApplication(exclude=DispatcherServletAutoConfiguration.class,scanBasePackages = {"infox.itmanager6.boot"})
@SpringBootApplication(exclude=DispatcherServletAutoConfiguration.class,scanBasePackages = {"com.infox.messagebook"})
// 开启缓存请把下行取消注释
//@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
