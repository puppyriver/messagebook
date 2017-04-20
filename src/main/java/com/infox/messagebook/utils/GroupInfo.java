package com.infox.messagebook.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Author: Ronnie.Chen
 * Date: 2016/10/15
 * Time: 15:03
 * rongrong.chen@alcatel-sbell.com.cn
 */
public class GroupInfo implements Serializable {
    private int total;
    private int process;
    private int success;
    private int fail;

    public GroupInfo(int total, int process, int success, int fail) {
        this.total = total;
        this.process = process;
        this.success = success;
        this.fail = fail;
    }

    public void update(int total, int process, int success, int fail) {
        this.total = total;
        this.process = process;
        this.success = success;
        this.fail = fail;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }
}
