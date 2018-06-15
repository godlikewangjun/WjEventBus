package com.wj.eventbus;

/**
 * @author Admin
 * @version 1.0
 * @date 2017/6/15
 */

public interface EventLister<T> {
    public void postResult(T eventVaule);
}
