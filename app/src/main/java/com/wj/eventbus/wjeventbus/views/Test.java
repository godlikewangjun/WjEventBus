package com.wj.eventbus.wjeventbus.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.eventbus.WjEventBus;
import com.wj.eventbus.wjeventbus.R;


/**
 * @author Admin
 * @version 1.0
 * @date 2017/6/16
 */

public class Test extends Activity{
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
        WjEventBus.getInit().post("1", "发送了:" + num);
        WjEventBus.getInit().post("2", "发送了:" + num);
        WjEventBus.getInit().post("3", "发送了:" + num);
    }
}
