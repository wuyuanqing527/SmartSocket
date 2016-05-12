package com.wuyuanqing.smartsocket.model;

/**
 * Created by wyq on 2016/5/11.
 */
public class TimerBean extends User{

    private String startTime="";
    private String endTime="";
    private boolean onOff=false;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", onOff=" + onOff +
                '}';
    }
}
