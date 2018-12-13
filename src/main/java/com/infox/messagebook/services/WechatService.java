package com.infox.messagebook.services;/**
 * Created by Chenrongrong on 2018/12/12.
 */

import com.infox.messagebook.model.XMessage;
import com.infox.messagebook.modules.wechat.MessageUtil;
import com.infox.messagebook.modules.wechat.TextMessageUtil;
import com.infox.messagebook.repository.XMessageRepository;
import com.infox.messagebook.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class WechatService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XMessageRepository xMessageRepository;


    public String handleImageMessage(String body) {
        Map<String,String> map = MessageUtil.xmlToMap(body);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String url = map.get("PicUrl");
        TextMessageUtil textMessage = new TextMessageUtil();

        try {
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png";
            HttpUtils.downLoadFromUrl(url, fileName,"files");
            return  textMessage.initMessage(FromUserName, ToUserName,"图片已保存:"+fileName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return  textMessage.initMessage(FromUserName, ToUserName,"图片获取失败:"+e.getMessage());

        }
    }
    public String handleTextMessage(String body) {
        Map<String,String> map = MessageUtil.xmlToMap(body);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String Content = map.get("Content");

        if (Content.startsWith("?")) {
            return new TextMessageUtil().initMessage(FromUserName,ToUserName,"1. 回复[1] 获取消息列表\r\n 2. 回复[2] 获取图片列表");
        }

        if (Content.equals("1") || Content.startsWith("1-")) {
            return new TextMessageUtil().initMessage(FromUserName,ToUserName,"http://www.message6.com/");
        }
        if (Content.equals("2") || Content.startsWith("2-")) {
            return new TextMessageUtil().initMessage(FromUserName,ToUserName,"http://www.message6.com/files.html");
        }

        XMessage xMessage = new XMessage();
        xMessage.setTime(new Date());
        xMessage.setContent(Content);
        xMessageRepository.save(xMessage);

        TextMessageUtil textMessage = new TextMessageUtil();
        String message = textMessage.initMessage(FromUserName, ToUserName,"消息已经保存");
        String content_exp = "<xml> <ToUserName><![CDATA["+ToUserName+"]]></ToUserName> <FromUserName><![CDATA["+FromUserName+"]]></FromUserName> <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[你好ssssss]]></Content> </xml>";


        logger.info("content_exp:"+content_exp);
        logger.info("message:"+message);

        content_exp = message.replaceAll(" ","");
        return content_exp;
    }
}
