package com.wj.eventbus;

/**
 * 事件值
 * @author Admin
 * @version 1.0
 * @date 2017/6/16
 */

public class EventKey {
    /**
     * 广播编号
     */
    public String code;
    /**
     * 优先级
     */
    public int priority;
    /**
     * id标识
     */
    public long id;

    public EventKey(String code, int priority, long id) {
        this.code = code;
        this.priority = priority;
        this.id = id;
    }
}
