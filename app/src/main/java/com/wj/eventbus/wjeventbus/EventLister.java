package com.wj.eventbus.wjeventbus;

/**
 * @author Admin
 * @version 1.0
 * @date 2017/6/15
 */

public abstract interface EventLister<T> {
    public void postResult(T eventVaule);
}
