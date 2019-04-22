package com.wj.eventbus.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import com.wj.eventbus.mylibrary.IEventAidlInterface;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019/1/3
 */
public class AidlServerTools {
    public IEventAidlInterface iEventAidlInterface;
    private boolean isBind;
    private ServiceConnection serviceConnection;
    private static AidlServerTools aidlServerTools;

    public static AidlServerTools getInit() {
        if (aidlServerTools == null) aidlServerTools = new AidlServerTools();
        return aidlServerTools;
    }

    public AidlServerTools() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iEventAidlInterface = IEventAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iEventAidlInterface = null;
            }
        };
    }

    public AidlServerTools bindService(Context context) {
        isBind = true;
        Intent intent = new Intent(context, EventServerService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        return this;
    }

    public void unbindService(Context context) {
        if (isBind) {
            isBind = false;
            context.unbindService(serviceConnection);
        }

    }
}
