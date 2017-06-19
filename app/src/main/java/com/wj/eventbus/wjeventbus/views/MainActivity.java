package com.wj.eventbus.wjeventbus.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wj.eventbus.wjeventbus.EventLister;
import com.wj.eventbus.wjeventbus.R;
import com.wj.eventbus.wjeventbus.WjEventBus;


import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WjEventBus.getInit().post("0","425");
        WjEventBus.getInit().subscribe("0", String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("优先级是0-----------");
                    }
                });
            }
        });
        WjEventBus.getInit().subscribe("0",2, String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("优先级是2-----------");
                        Intent intent=new Intent(MainActivity.this,Test.class);
                        startActivity(intent);
                    }
                });
            }
        });
        WjEventBus.getInit().subscribe("0",1, String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("优先级是1-----------");
                    }
                });
            }
        });
        WjEventBus.getInit().subscribe("0",0, String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("优先级是0-----------");
                    }
                });
            }
        });
        WjEventBus.getInit().subscribeNext("0",4,String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------4粘性动画"+eventVaule);
            }
        });
        WjEventBus.getInit().subscribeNext("0",5,String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------5粘性动画"+eventVaule);
                WjEventBus.getInit().subscribeNext("0",4,String.class, new EventLister() {
                    @Override
                    public void postResult(final Object eventVaule) {
                        System.out.println("--------4粘性动画"+eventVaule);
                    }
                });
            }
        });
        WjEventBus.getInit().subscribeNext("0",3,String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                        System.out.println("--------3粘性动画"+eventVaule);
            }
        });
        WjEventBus.getInit().subscribeNext("0",4,String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------4粘性动画"+eventVaule);
            }
        });
        WjEventBus.getInit().post("0","425");

        WjEventBus.getInit().subscribe("1", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eventVaule+"----------接收");
                    }
                });
            }
        });
        WjEventBus.getInit().subscribe("2", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eventVaule+"----------接收");
                    }
                });
            }
        });
        WjEventBus.getInit().subscribe("2",2, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eventVaule+"----------优先级2接收");
                    }
                });
            }
        });
        WjEventBus.getInit().subscribe("3", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eventVaule+"----------接收");
                    }
                });
            }
        });

        WjEventBus.getInit().remove("1");
        WjEventBus.getInit().remove("2",2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁整个注册
        WjEventBus.getInit().destory();
    }
}
