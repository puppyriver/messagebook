package com.infox.messagebook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infox.messagebook.model.XMessage;
import com.infox.messagebook.repository.XMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Author: Ronnie.Chen
 * Date: 2017/3/16
 * Time: 14:56
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Controller
@RequestMapping("/api/*")
public class ApiControler {
    
    @Autowired
    private XMessageRepository xMessageRepository;
    private Logger logger = LoggerFactory.getLogger(ApiControler.class);

    @RequestMapping(value = "listSms", method = {POST,GET})
    public @ResponseBody
    List<XMessage> listSms() {
//        XMessage message = new XMessage();
//        message.setId(1l);
//        message.setContent("ronnieronnie");
//        List list = Arrays.asList(message);
//
//        return list;
        return xMessageRepository.findAllByCategory("SMS",new Sort(Sort.Direction.DESC,"time"));
    }

    @RequestMapping(value = "sms", method = {POST,GET})
    public  @ResponseBody XMessage sms(String number,String content) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            content = URLDecoder.decode(content,"utf-8");
            XMessage xmessage = new XMessage();
            xmessage.setContent(number+":\n"+content);
            xmessage.setCategory("SMS");
            xmessage.setTime(new Date());
            return xMessageRepository.save(xmessage);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value = "delete", method = {POST,GET})
    public  @ResponseBody XMessage save(@RequestParam Map request,@RequestParam Long messageId) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
//            message = URLDecoder.decode(message,"utf-8");
//            XMessage xmessage = objectMapper.readValue(message,XMessage.class);
             xMessageRepository.delete(messageId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    
}
