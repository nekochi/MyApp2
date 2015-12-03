package com.nekomimi.api;

/**
 * Created by hongchi on 2015-11-11.
 * File description :
 */
public class ApiResponse<T>
{
    private String event;
    private String msg;
    private T obj;

    public ApiResponse(){}

    public ApiResponse(String event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return event.equals("0");
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
