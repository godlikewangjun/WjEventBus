package com.wj.eventbus.aidl.methons;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.wj.eventbus.EventLister;
import com.wj.eventbus.WjEventBus;
import com.wj.eventbus.mylibrary.IEventAidlInterface;
import com.wj.eventbus.mylibrary.IEventListener;
import com.wj.eventbus.mylibrary.IEventMsgType;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019/1/3
 */
public class AidlServerEventBus extends IEventAidlInterface.Stub {
    RemoteCallbackList<IEventListener> mListenerList = new RemoteCallbackList<>();
    @Override
    public void subscribe(String code, final IEventMsgType data) throws RemoteException {
        WjEventBus.getInit().remove(code);
        WjEventBus.getInit().removeMsg(code);
        WjEventBus.getInit().subscribe(code, new EventLister<String>() {
            @Override
            public void postResult(String eventValue) {
                synchronized (mListenerList) {
                    int n = mListenerList.beginBroadcast();
                    try {
                        for (int i = 0; i < n; i++) {
                            IEventListener listener = mListenerList.getBroadcastItem(i);
                            if (listener != null ) {
                                listener.postResult(eventValue);
                            }
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    mListenerList.finishBroadcast();
                }
            }
        });
        registerCallBack(data.getEventLister());
    }

    @Override
    public void post(String code, IEventMsgType data) throws RemoteException {
        WjEventBus.getInit().post(code,data.getData());
    }

    @Override
    public void registerCallBack(IEventListener cb) throws RemoteException {
        mListenerList.register(cb);
    }

    @Override
    public void unregisterCallBack(IEventListener cb) throws RemoteException {
        mListenerList.unregister(cb);
    }
}
