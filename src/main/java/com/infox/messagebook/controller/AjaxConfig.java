package com.infox.messagebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Author: Ronnie.Chen
 * Date: 2017/2/21
 * Time: 16:36
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Configuration
@ComponentScan
@EnableWebMvc
public class AjaxConfig {
    private Logger logger = LoggerFactory.getLogger(AjaxConfig.class);


}
