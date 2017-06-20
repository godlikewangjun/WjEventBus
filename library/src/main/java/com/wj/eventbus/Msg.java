package com.wj.eventbus;

/**
 * Created by Android on 2016/6/15.
 */
public class Msg {
    public String code;
    public Object object;

    public Msg(String code, Object object){
        this.code = code;
        this.object = object;
    }

}
