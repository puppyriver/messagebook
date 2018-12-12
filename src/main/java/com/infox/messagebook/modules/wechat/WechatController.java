package com.infox.messagebook.modules.wechat;


import com.infox.messagebook.utils.SHA1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;


//import org.aspectj.bridge.MessageUtil;

@Controller
@RequestMapping("/open/wechat/*")
public class WechatController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping(value = "signature", method = {RequestMethod.GET,RequestMethod.POST})
    public void  signature(@RequestBody(required = false) String body, HttpServletRequest request, HttpServletResponse response) {

        logger.info("wechat-------------------------------------------------------");
        logger.info("@@ body = "+body );
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            logger.info("@@ "+key+" = "+value);
        }
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        logger.info("signature = "+signature);
        logger.info("timestamp = "+timestamp);
        logger.info("nonce = "+nonce);
        logger.info("echostr = "+echostr);


//        String content_exp = "<xml> <ToUserName><![CDATA[toUser]]></ToUserName> <FromUserName><![CDATA[fromUser]]></FromUserName> <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[你好]]></Content> </xml>";

//        Document parse = XMLUtil.parse(new ByteArrayInputStream(content_exp.getBytes()));
        if (body == null) {
            if( checkSignature(signature, timestamp, nonce)){


                response(echostr,response);
            }
        } else  {

            Map<String,String> map = MessageUtil.xmlToMap(body);
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");

            TextMessageUtil textMessage = new TextMessageUtil();
            String message = textMessage.initMessage(FromUserName, ToUserName);
            String content_exp = "<xml> <ToUserName><![CDATA["+ToUserName+"]]></ToUserName> <FromUserName><![CDATA["+FromUserName+"]]></FromUserName> <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[你好ssssss]]></Content> </xml>";

            logger.info("content_exp:"+content_exp);
            logger.info("message:"+message);
            content_exp = message.replaceAll(" ","");
            response.setContentType("application/xml;charset=utf-8");
            response(content_exp,response);
        }



    }

    private void response(String content,HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(content);
            logger.info("Response = "+content);
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(),e);
        }finally{
            out.close();
        }
    }


    private static final String token = "cngoodbuy0513";
    public static boolean checkSignature(String signature,String timestamp,String nonce) {
        String[] str = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }

    public static void main(String[] args) throws IOException {
//        String content_exp = "<xml> <ToUserName><![CDATA[toUser]]></ToUserName> <FromUserName><![CDATA[fromUser]]></FromUserName> <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[你好]]></Content> </xml>";
//
//        Document parse = XMLUtil.parse(new ByteArrayInputStream(content_exp.getBytes()));
//
//        System.out.println(parse);
    }

}
