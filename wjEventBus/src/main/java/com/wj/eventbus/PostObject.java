package com.wj.eventbus;

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
    public Class<?> aClass;

    public PostObject() {
    }

    public PostObject(int priority,Class<?> aClass, EventLister eventLister) {
        this.priority = priority;
        this.eventLister = eventLister;
        this.aClass=aClass;
    }
}
