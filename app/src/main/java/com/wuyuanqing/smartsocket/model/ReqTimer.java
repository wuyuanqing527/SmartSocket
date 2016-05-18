package com.wuyuanqing.smartsocket.model;

/**
 * Created by wyq on 2016/5/18.
 */
public class ReqTimer {
    private String action="";
    private TimerBean timerBean=null;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TimerBean getTimerBean() {
        return timerBean;
    }

    public void setTimerBean(TimerBean timerBean) {
        this.timerBean = timerBean;
    }
}
