// IEventListener.aidl
package com.wj.eventbus.mylibrary;
// 监听进程的回调

interface IEventListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void postResult(String eventValue);
}
