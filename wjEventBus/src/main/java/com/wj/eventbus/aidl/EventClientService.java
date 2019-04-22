package com.wj.eventbus.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.wj.eventbus.aidl.methons.AidlClientEventBus;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019/1/3
 */
public class EventClientService extends Service {
    private AidlClientEventBus aidlEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        aidlEventBus=new AidlClientEventBus();
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.wjevent.permission.ACCESS_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return aidlEventBus;
    }
}
