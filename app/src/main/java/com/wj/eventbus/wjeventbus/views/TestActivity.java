package com.wj.eventbus.wjeventbus.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.wj.eventbus.WjEventBus;
import com.wj.eventbus.aidl.AidlClientTools;
import com.wj.eventbus.aidl.AidlServerTools;
import com.wj.eventbus.mylibrary.IEventAidlInterface;
import com.wj.eventbus.mylibrary.IEventMsgType;
import com.wj.eventbus.wjeventbus.R;


/**
 * @author Admin
 * @version 1.0
 * @date 2017/6/16
 */

public class TestActivity extends Activity{
    int num=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num==0){
                    doSend();
                }
            }
        });
    }
    private void doSend(){
        AidlClientTools aidlClientTools= AidlClientTools.getInit().bindService(TestActivity.this);
        try {
            IEventMsgType iEventMsgType=new IEventMsgType();
            iEventMsgType.setO(String.class);
            iEventMsgType.setData("测试");
            if(aidlClientTools.iEventAidlInterface!=null)aidlClientTools.iEventAidlInterface.post("ce",iEventMsgType);
            AidlServerTools.getInit().iEventAidlInterface.post("ce",iEventMsgType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        WjEventBus.getInit().post("1", "发送了:" + num);
//        WjEventBus.getInit().post("2", "发送了:" + num);
//        WjEventBus.getInit().post("3", "发送了:" + num);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AidlServerTools.getInit().iEventAidlInterface.unregisterCallBack();
        WjEventBus.getInit().remove("ce");
        WjEventBus.getInit().removeMsg("ce");
    }
}
