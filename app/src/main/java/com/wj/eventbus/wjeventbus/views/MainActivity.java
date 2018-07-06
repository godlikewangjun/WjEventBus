package com.wj.eventbus.wjeventbus.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.wj.eventbus.EventLister;
import com.wj.eventbus.WjEventBus;
import com.wj.eventbus.wjeventbus.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WjEventBus.getInit().post("0", "425");
        WjEventBus.getInit().subscribe("0", String.class, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是0-----------");
            }
        });
        WjEventBus.getInit().subscribe("0", String.class,2, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是2-----------");
            }
        });
        WjEventBus.getInit().subscribe("0",  String.class,1, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是1-----------");
            }
        });
        WjEventBus.getInit().subscribe("0",  String.class,0, new EventLister() {
            @Override
            public void postResult(Object eventVaule) {
                System.out.println("优先级是0-----------");
            }
        });
        WjEventBus.getInit().subscribeNext("0", 4, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------4粘性动画" + eventVaule);
            }
        });
        WjEventBus.getInit().subscribeNext("0", 5, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------5粘性动画" + eventVaule);
                WjEventBus.getInit().subscribeNext("0", 4, String.class, new EventLister() {
                    @Override
                    public void postResult(final Object eventVaule) {
                        System.out.println("--------4粘性动画" + eventVaule);
                    }
                });
            }
        });
        WjEventBus.getInit().subscribeNext("0", 3, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------3粘性动画" + eventVaule);
            }
        });
        WjEventBus.getInit().subscribeNext("0", 4, String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println("--------4粘性动画" + eventVaule);
            }
        });
        WjEventBus.getInit().post("0", "425");

        WjEventBus.getInit().subscribe("1", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------接收");
            }
        });
        WjEventBus.getInit().subscribe("2", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------接收");
            }
        });
        WjEventBus.getInit().subscribe("2",  String.class,2, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------优先级2接收");
            }
        });
        WjEventBus.getInit().subscribe("3", String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                System.out.println(eventVaule + "----------接收");
            }
        });
        WjEventBus.getInit().subscribe("3", new EventLister<String>() {
            @Override
            public void postResult(final String eventVaule) {
                System.out.println(eventVaule + "------sdsdsdsd----接收");
            }
        });
        WjEventBus.getInit().remove("1");
        WjEventBus.getInit().remove("2", 2);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁整个注册
        WjEventBus.getInit().destory();
    }
}
