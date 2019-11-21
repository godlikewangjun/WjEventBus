package com.wj.eventbus.mylibrary;
import com.wj.eventbus.mylibrary.IEventMsgType;
import com.wj.eventbus.mylibrary.IEventListener;

interface IEventAidlInterface {
    void  subscribe(in String code,in IEventMsgType data);

     void  post(in String code,in IEventMsgType data);

     void registerCallBack(IEventListener cb);
     void unregisterCallBack(IEventListener cb);
}
