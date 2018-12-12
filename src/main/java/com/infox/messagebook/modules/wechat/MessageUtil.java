package com.infox.messagebook.modules.wechat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    public static Map<String,String> xmlToMap(HttpServletRequest request)
    {
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream in = null;
        try {
            in = request.getInputStream();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;


    }
    public static Map<String,String> xmlToMap(String xml)
    {
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream in = null;
        try {
            in = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        }  catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;


    }

    public static void main(String[] args) {
        String xml = "<xml><ToUserName><![CDATA[gh_142096d1161c]]></ToUserName>\n" +
                "<FromUserName><![CDATA[or2050h2v56uiGU-q1iYVhaFCc0s]]></FromUserName>\n" +
                "<CreateTime>1543711670</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[2]]></Content>\n" +
                "<MsgId>6630191137528614439</MsgId>\n" +
                "</xml>\n";
        Map<String, String> stringStringMap = MessageUtil.xmlToMap(xml);
        System.out.println(stringStringMap);
    }

}