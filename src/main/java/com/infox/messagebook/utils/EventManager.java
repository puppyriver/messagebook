package com.infox.messagebook.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2016/9/15.
 */
public class EventManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static EventManager ourInstance = new EventManager();

    public static EventManager getInstance() {
        return ourInstance;
    }

    private EventManager() {
    }

    private HashMap<String,Listener> listeners = new HashMap<>();

    public HashMap<String,GroupInfo> groups = new HashMap<String,GroupInfo>();
    private HashSet<String> groupsCanRemoveWhenLogsReaded = new HashSet<>();

    public boolean isGroupEnd(String group) {
        if (groupsCanRemoveWhenLogsReaded.contains(group)) {
            groups.remove(group);
            groupsCanRemoveWhenLogsReaded.remove(group);
            return true;
        }
        return !groups.containsKey(group);
    }

    public void groupEnd(String group) {
        groupsCanRemoveWhenLogsReaded.add(group);
    }

    public void addListener(String group,Listener listener) {
        this.listeners.put(group,listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    private HashMap<String,ArrayList> queues = new HashMap<>();

    public void addGroup(String group) {
        groups.put(group,new GroupInfo(0,0,0,0));
    }

    public void updateGroup(String group,int process,int total,int success,int failure) {
        GroupInfo groupInfo = groups.get(group);
        if (groupInfo != null)
            groupInfo.update(total,process,success,failure);
    }

    public void updateProcess(String group,int process) {
        GroupInfo groupInfo = groups.get(group);
        if (groupInfo != null)
            groupInfo.setProcess(process);
    }

    public void updateTotal(String group,int total) {
        GroupInfo groupInfo = groups.get(group);
        if (groupInfo != null)
            groupInfo.setTotal(total);
    }
    public void addSuccess(String group) {
       addSuccess(group,1);
    }
    public void addSuccess(String group,int number) {
        GroupInfo groupInfo = groups.get(group);
        if (groupInfo != null)
            groupInfo.setSuccess(groupInfo.getSuccess()+number);
    }
    public void addFail(String group) {
        addFail(group,1);
    }
    public void addFail(String group,int number) {
        GroupInfo groupInfo = groups.get(group);
        if (groupInfo != null)
            groupInfo.setFail(groupInfo.getFail()+number);
    }





    public List<Event> takeAll(String group) {
        synchronized (group) {
            List l = new ArrayList<>();
            ArrayList queue = queues.get(group);
            if (queue != null && queue.size() > 0) {
                l.addAll(queue);


            }
            for (Object o : l) {
                queue.remove(o);
            }
            return l;
        }


    }
    public void offer(Event event) {
        logger.debug("offer Event : "+event);
        String group = event.getGroup();
        ArrayList queue = queues.get(group);
        if (queue == null) {
            synchronized (queues) {
                queue = queues.get(group);
                if (queue == null) {
                    queue = new ArrayList();
                    queues.put(group,queue);
                }
            }
        }

        queue.add(event);
    }

    public interface Listener {
        public void onMessage(Event message) ;
    }
}
