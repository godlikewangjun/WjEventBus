***简单的事件分发***<br>
其实之前看过几种事件的分发，包括greenrobot的EventBus，还有rxjava写的事件订阅。前者是写法局限，事件使用太多容易混乱，后者是准备弃用rxjava，仅仅对于使用事件订阅来说太重，于是想着自己写个，满足不是太多的事件订阅。封装不是太多，只是提供一些思路，其实不是太复杂。简单的模仿了rxjava的事件订阅，因为习惯了。
实现了订阅集合，推送的集合和事件的集合。
使用方法主要是 推送消息

```
WjEventBus.getInit().post("0","425");
```
接收消息

```
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
```
粘性注册

```
 WjEventBus.getInit().subscribeNext("0",3,String.class, new EventLister() {
            @Override
            public void postResult(final Object eventVaule) {
                        System.out.println("--------3粘性动画"+eventVaule);
            }
        });
```
销毁整个注册 一般是在应用退出

```
 //销毁整个注册
        WjEventBus.getInit().destory();
```
移除某一个

```
 WjEventBus.getInit().remove("1");
 WjEventBus.getInit().remove("2",2);
```
具体详见[WjEventBus demo](https://github.com/godlikewangjun/WjEventBus)