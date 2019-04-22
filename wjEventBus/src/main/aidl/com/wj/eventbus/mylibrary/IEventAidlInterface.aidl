// IEventAidlInterface.aidl
package com.wj.eventbus.mylibrary;
import com.wj.eventbus.mylibrary.IEventMsgType;
import com.wj.eventbus.mylibrary.IEventListener;
// Declare any non-default types here with import statements

interface IEventAidlInterface {
    //订阅事件
    void  subscribe(in String code,in IEventMsgType data);

    //推送事件
     void  post(in String code,in IEventMsgType data);

     //注册回调接口
     void registerCallBack(IEventListener cb);
     void unregisterCallBack(IEventListener cb);
}
