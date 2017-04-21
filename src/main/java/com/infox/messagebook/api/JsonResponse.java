package com.infox.messagebook.api;
 

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * Author: Ronnie.Chen
 * Date: 2017/4/14
 * Time: 11:07
 * rongrong.chen@alcatel-sbell.com.cn
 */
public class JsonResponse implements Serializable ,Cloneable{
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_EXCEPTION = 1;
    public static final int STATUS_FATAL = 2;
    private int status = STATUS_SUCCESS;

    private String message = null;
    private Object data = null;

    public JsonResponse() {
    }

    public JsonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static JsonResponse SUCCESS() {
        return new JsonResponse(JsonResponse.STATUS_SUCCESS,"success");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(new JsonResponse());
    }
}
