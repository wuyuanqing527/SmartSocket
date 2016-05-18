package com.wuyuanqing.smartsocket.model;

import java.io.Serializable;

/**
 * Created by wyq on 2016/5/11.
 */
public class TimerBean implements Serializable {

    private String socketName = "";
    private String startTime = "";
    private String endTime = "";
    private boolean onOff = false;

    public String getSocketName() {
        return socketName;
    }

    public void setSocketName(String socketName) {
        this.socketName = socketName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    @Override
    public String toString() {
        return "TimerBean{" +
                "socketName='" + socketName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", onOff=" + onOff +
                '}';
    }
}
