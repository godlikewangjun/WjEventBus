package com.wj.eventbus.mylibrary;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.wj.eventbus.EventLister;

import java.io.Serializable;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019/1/2
 */
public class IEventMsgType implements Parcelable {
    Class<?> o;
    String data;
    String code;
    IEventListener eventLister;

    public Class<?> getO() {
        return o;
    }

    public void setO(Class<?> o) {
        this.o = o;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IEventListener getEventLister() {
        if(eventLister==null) {
            eventLister = new IEventListener.Stub() {
                @Override
                public void postResult(String eventValue) throws RemoteException {

                }
            };
        }
        return eventLister;
    }

    public void setEventLister(IEventListener eventLister) {
        this.eventLister = eventLister;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.o);
        if(this.data!=null)dest.writeString(this.data);
        dest.writeSerializable(this.code);
    }

    public IEventMsgType() {
    }

    protected IEventMsgType(Parcel in) {
        this.o = (Class<?>) in.readSerializable();
        this.data = in.readString();
        this.code = in.readString();
    }

    public static final Creator<IEventMsgType> CREATOR = new Creator<IEventMsgType>() {
        @Override
        public IEventMsgType createFromParcel(Parcel source) {
            return new IEventMsgType(source);
        }

        @Override
        public IEventMsgType[] newArray(int size) {
            return new IEventMsgType[size];
        }
    };
}
