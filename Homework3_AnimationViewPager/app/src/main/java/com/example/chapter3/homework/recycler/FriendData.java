package com.example.chapter3.homework.recycler;

public class FriendData {
    private String remark;
    private int srcPortrait;

    public FriendData(String remark, int id) {
        this.remark = remark;
        this.srcPortrait = id;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSrcPortrait() {
        return srcPortrait;
    }

    public void setSrcPortrait(int srcPortrait) {
        this.srcPortrait = srcPortrait;
    }
}
