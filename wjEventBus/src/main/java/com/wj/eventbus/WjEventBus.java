package com.wj.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息分发
 *
 * @author Admin
 * @version 1.0
 * @date 2017/6/15
 */

public class WjEventBus {
    /**
     * 单例
     */
    private static volatile WjEventBus wjEventBus;
    /**
     * 消息推送的集合
     */
    private ConcurrentHashMap<EventKey, Object> posts = new ConcurrentHashMap<>();
    /**
     * 订阅的集合
     */
    private ConcurrentHashMap<EventKey, Class<?>> subscribes = new ConcurrentHashMap<>();
    /**
     * 事件回调的集合
     */
    private ConcurrentHashMap<EventKey, EventLister> listener = new ConcurrentHashMap<>();
    /**
     * 粘性事件的分发暂时缓冲区
     */
    private ArrayList<PostObject> stickyEventListers = new ArrayList<>();
    private int priority = 0;//优先级默认是0
    public long id = 0;//id 递增
    private int index = -1;//下标

    public static WjEventBus getInit() {
        if (wjEventBus == null) {
            wjEventBus = new WjEventBus();
        }
        wjEventBus.id++;
        return wjEventBus;
    }

    /**
     * 订阅事件
     * @param code
     * @param o 事件类型
     * @param eventListe
     * @return
     */
    public WjEventBus subscribe(String code, Class<?> o,EventLister eventLister) {
        EventKey eventKey = new EventKey(code, priority, id);
        subscribes.put(eventKey, o);
        listener.put(eventKey, eventLister);
        return wjEventBus;
    }

    /**
     * 订阅粘性事件
     * @param code
     * @param o
     * @param eventListe
     * @return
     */
    public WjEventBus subscribeNext(String code, Class<?> o, EventLister eventLister) {
        EventKey eventKey = new EventKey(code, priority, id);
        subscribes.put(eventKey, o);
        listener.put(eventKey, eventLister);
        Iterator iterator = posts.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(code)) {
                eventLister.postResult(o);
                break;
            }
        }
        return wjEventBus;
    }

    /**
     * 订阅事件 带入优先级
     *
     * @param code
     * @param priority   优先级
     * @param o
     * @param eventListe
     * @return
     */
    public WjEventBus subscribe(String code, int priority, Class<?> o, EventLister eventLister) {
        EventKey eventKey = new EventKey(code, priority, id);
        subscribes.put(eventKey, o);
        listener.put(eventKey, eventLister);
        return wjEventBus;
    }

    /**
     * 粘性事件订阅 带入优先级
     *
     * @param code
     * @param priority   优先级
     * @param o
     * @param eventListe
     * @return
     */
    public WjEventBus subscribeNext(String code, int priority, Class<?> o, EventLister eventLister) {
        EventKey eventKey = new EventKey(code, priority, id);
        subscribes.put(eventKey, o);
        listener.put(eventKey, eventLister);
        //存入缓存
        stickyEventListers.add(new PostObject(priority,o, eventLister));

        //排序
        Collections.sort(stickyEventListers, new Comparator<PostObject>() {
            @Override
            public int compare(PostObject o1, PostObject o2) {
                return o2.priority - o1.priority;
            }
        });

        Iterator iterator = posts.keySet().iterator();
        //处理事件
        while (stickyEventListers.size() > 0 && index > -1) {
            deStickyEvent(eventKey, iterator, code, o);
            if (stickyEventListers.size() < 1) {
                index = -1;
                break;
            }
        }
        return wjEventBus;
    }

    /**
     * 推送消息
     * 如果存在优先级就按照最大的推，不存在就全部推送。优先级默认是priority
     *
     * @param code
     * @param o
     */
    public synchronized void post(String code, Object o) {
        EventKey eventKey = new EventKey(code, priority, 0);
        posts.put(eventKey, new Msg(code, o));
        //处理事件
        Iterator iterator = subscribes.keySet().iterator();
        deEvent(eventKey, iterator, code, o);
    }

    /**
     * 处理事件的分发
     * 按照优先级从高到低的传递
     *
     * @param iterator
     * @param code
     */
    private void deEvent(EventKey eventKey, Iterator iterator, String code, Object o) {
        ArrayList<PostObject> eventListers = new ArrayList<>();//待发事件的集合
        long id = 0;//如果有排序的广播就取得ID值
        PostObject postObject;
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(code)) {
                postObject = new PostObject();
                postObject.priority = aClass.priority;
                postObject.eventLister = listener.get(aClass);
                eventListers.add(postObject);
            }
            if (aClass.code.equals(code)) {
                id = aClass.id;
            }
        }
        //排序
        Collections.sort(eventListers, new Comparator<PostObject>() {
            @Override
            public int compare(PostObject o1, PostObject o2) {
                return o2.priority - o1.priority;
            }
        });
        for (int i = 0; i < eventListers.size(); i++) {
            eventListers.get(i).eventLister.postResult(o);
        }
    }

    /**
     * 处理粘性事件的分发
     * 只会取得最后一条广播
     *
     * @param iterator
     * @param code
     */
    private void deStickyEvent(EventKey eventKey, Iterator iterator, String code, Object o) {
        EventLister eventLister = null;//待发事件的集合
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(code)) {
                eventLister = listener.get(aClass);
                break;
            }
        }
        if (eventLister != null) {
            eventLister.postResult(o);
        }
    }

    /**
     * 移除某一个事件
     *
     * @param code 标识
     */
    public void remove(String tag) {
        //移除订阅
        Iterator iterator = subscribes.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(tag)) {
                subscribes.remove(aClass);
            }
        }
        //移除推送消息
        iterator = posts.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(tag)) {
                posts.remove(aClass);
            }
        }
        //移除监听消息
        iterator = listener.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(tag)) {
                listener.remove(aClass);
            }
        }
    }

    /**
     * 移除某一个事件
     * @param tag 标识
     * @param priority 优先级
     */
    public void remove(String tag, int priority) {
        //移除订阅
        Iterator iterator = subscribes.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(tag) && aClass.priority == priority) {
                subscribes.remove(aClass);
            }
        }
        //移除推送消息
        iterator = posts.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(tag) && aClass.priority == priority) {
                posts.remove(aClass);
            }
        }
        //移除监听消息
        iterator = listener.keySet().iterator();
        while (iterator.hasNext()) {
            EventKey aClass = (EventKey) iterator.next();
            if (aClass.code.equals(tag) && aClass.priority == priority) {
                listener.remove(aClass);
            }
        }
    }

    public void changType(Class<?> o) throws NoSuchMethodException {
        //通过反射获取到方法
        Method declaredMethod =o.getDeclaredMethod("postResult", Object.class);
        //获取到方法的参数列表
        Type[] parameterTypes = declaredMethod.getGenericParameterTypes();
        for (Type type : parameterTypes) {
            System.out.println(type+" --=======");
            //只有带泛型的参数才是这种Type，所以得判断一下
            if(type instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) type;
                //获取参数的类型
                System.out.println(parameterizedType.getRawType()+" --=======");
                //获取参数的泛型列表
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type type2 : actualTypeArguments) {
                    System.out.println(type2);
                }
            }
        }
    }
    /**
     * 销毁整个事件的监听
     */
    public void destory() {
        subscribes.clear();
        posts.clear();
        listener.clear();
    }
}
