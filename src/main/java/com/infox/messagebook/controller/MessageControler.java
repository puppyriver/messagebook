package com.infox.messagebook.controller;

import com.infox.messagebook.model.XMessage;
import com.infox.messagebook.repository.XMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return xMessageRepository.findAll();
    }

    
}
