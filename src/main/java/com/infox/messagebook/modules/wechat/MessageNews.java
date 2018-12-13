package com.infox.messagebook.modules.wechat;

import java.util.List;

public class MessageNews extends BaseMessage {

    private Integer ArticleCount;
    private List<Article> Articles;

    private String MsgId;// 消息id，64位整型

    public MessageNews() {

    }

    public MessageNews(String toUserName, String fromUserName, long createTime, String msgType, List<Article> articles,
                       String msgId) {
        super();
        ToUserName = toUserName;
        FromUserName = fromUserName;
        CreateTime = createTime;
        MsgType = msgType;
        MsgId = msgId;
        this.Articles = articles;
    }

 
    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;

    }
}