package com.infox.messagebook;


import com.infox.messagebook.controller.AjaxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: Ronnie.Chen
 * Date: 2017/1/23
 * Time: 12:46
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Configuration
public class AppConfig implements ApplicationContextAware{
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);


    @Autowired
    private Environment env;



//    @Bean
//    public SmasAlarmService alarmServer() {
//        return new SmasAlarmService();
//    }
//
//    @Bean
//    public RuleManager ruleManager()  {
//        return new RuleManager();
//    }
//
//    @ConfigurationProperties(prefix="cdcp")
//    @Bean
//    public TrapServer trapServer()  {
//        return new TrapServer();
//    }
//
//    @Bean
//    public PmsServer pmServer() {
//        System.getProperties().list(System.out);
//        System.setProperty("jdbc.pms.driverClassName",env.getProperty("spring.dataSource.driverClassName"));
//        System.setProperty("jdbc.pms.url",env.getProperty("spring.dataSource.url"));
//        System.setProperty("jdbc.pms.username",env.getProperty("spring.dataSource.username"));
//        System.setProperty("jdbc.pms.password",env.getProperty("spring.dataSource.password"));
//
//        PmsServer server = new PmsServer();
//        //server.setPmReceiver(new PMReceiver());
//        server.setPmCache(new H2PMCache());
//        server.setPmDataListeners(Arrays.asList((PM_DATA_Listener) ruleManager()));
//        return server;
//    }
//
//
//    @Bean(name = "/PmServer.json")
//    public JsonServiceExporter exporter() {
//        JsonServiceExporter exporter = new JsonServiceExporter();
//        exporter.setService(pmServer());
//        exporter.setServiceInterface(PMServerAPI.class);
//        return exporter;
//    }
//
//    @Bean
//    public WebContext webContext() {
//        WebContext webContext = new WebContext();
//        webContext.setModules(Arrays.asList(ruleManager(),alarmServer()));
//        logger.info(env.toString());
//        return webContext;
//    }

    @Bean
    public ArrayList boPlugins() {
        return new ArrayList(Arrays.asList());
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      //  SpringContext.getInstance().setApplicationContext(applicationContext);
    }

    @Bean
    public ServletRegistrationBean ajax() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(AjaxConfig.class);
        dispatcherServlet.setApplicationContext(applicationContext);
    //    applicationContext.setParent(SpringContext.getInstance().getApplicationContext());
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/ajax/*");
        servletRegistrationBean.setName("ajax");
        return servletRegistrationBean;
    }

//    @Value("${spring}")
//    private Object spring;
//
//    @Bean
//    public Object appSpringConfig() {
//        return spring;
//    }



}
