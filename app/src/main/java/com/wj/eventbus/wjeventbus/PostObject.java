package com.wj.eventbus.wjeventbus;

/**
 * @author Admin
 * @version 1.0
 * @date 2017/6/19
 */

public class PostObject {
    /**
     * 优先级
     */
    public int priority;
    public EventLister eventLister;

    public PostObject() {
    }

    public PostObject(int priority, EventLister eventLister) {
        this.priority = priority;
        this.eventLister = eventLister;
    }
}
