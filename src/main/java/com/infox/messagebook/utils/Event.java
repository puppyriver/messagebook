package com.infox.messagebook.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/15.
 */
public class Event implements Serializable {
    private String serialId;
    private String group;

    public Event(String serialId, String group) {
        this.serialId = serialId;
        this.group = group;
    }



    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
