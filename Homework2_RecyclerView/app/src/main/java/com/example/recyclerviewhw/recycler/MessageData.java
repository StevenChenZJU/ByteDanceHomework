package com.example.recyclerviewhw.recycler;


public class MessageData {

    String remark;
    String message;
    String time;
    Integer msgNumber;

    public MessageData(String remark, String message, String time, Integer msgNumber) {
        this.remark = remark;
        this.message = message;
        this.time = time;
        this.msgNumber = msgNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMsgNumber() {
        return msgNumber;
    }

    public void setMsgNumber(Integer msgNumber) {
        this.msgNumber = msgNumber;
    }
}