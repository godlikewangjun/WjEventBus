package com.wj.eventbus.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;

import com.wj.eventbus.aidl.methons.AidlServerEventBus;
import com.wj.eventbus.mylibrary.IEventListener;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019/1/3
 */
public class EventServerService extends Service {
    private AidlServerEventBus aidlEventBus;
    @Override
    public void onCreate() {
        super.onCreate();
        aidlEventBus=new AidlServerEventBus();
    }

    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission(getPackageName()+".permission.ACCESS_SERVICE");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            return null;
//        }
        return aidlEventBus;
    }
}
