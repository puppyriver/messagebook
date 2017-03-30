package com.infox.messagebook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infox.messagebook.model.XMessage;
import com.infox.messagebook.repository.XMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * Author: Ronnie.Chen
 * Date: 2017/3/16
 * Time: 14:56
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Controller
@RequestMapping("/message/*")
public class MessageControler {
    
    @Autowired
    private XMessageRepository xMessageRepository;
    private Logger logger = LoggerFactory.getLogger(MessageControler.class);

    @RequestMapping(value = "{userName}", method = {POST,GET})
    public @ResponseBody
    List<XMessage> showList(@PathVariable String userName, @RequestParam(defaultValue = "1") int page, Model model ) {
//        XMessage message = new XMessage();
//        message.setId(1l);
//        message.setContent("ronnieronnie");
//        List list = Arrays.asList(message);
//
//        return list;
        return xMessageRepository.findAll(new Sort(Sort.Direction.DESC,"time"));
    }

    @RequestMapping(value = "save", method = {POST,GET})
    public  @ResponseBody XMessage save(@RequestParam Map request,@RequestParam String message) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = URLDecoder.decode(message,"utf-8");
            XMessage xmessage = objectMapper.readValue(message,XMessage.class);
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
