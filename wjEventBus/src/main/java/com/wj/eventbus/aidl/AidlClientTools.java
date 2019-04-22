package com.wj.eventbus.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.wj.eventbus.mylibrary.IEventAidlInterface;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019/1/3
 */
public class AidlClientTools {
    public static IEventAidlInterface iEventAidlInterface;
    private boolean isBind;
    private ServiceConnection serviceConnection;
    private static AidlClientTools aidlClientTools;

    public static AidlClientTools getInit() {
        if (aidlClientTools == null) aidlClientTools = new AidlClientTools();
        return aidlClientTools;
    }

    public AidlClientTools() {
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

    public AidlClientTools bindService(Context context) {
        if (isBind) return this;
        isBind = true;
        Intent intent = new Intent(context, EventClientService.class);
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
