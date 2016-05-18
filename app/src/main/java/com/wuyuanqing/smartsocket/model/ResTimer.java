package com.wuyuanqing.smartsocket.model;

import java.util.List;

/**
 * Created by wyq on 2016/5/18.
 */
public class ResTimer {
    private int resultCode=-1;
    private List<TimerBean> timerBeans;

    public List<TimerBean> getTimerBeans() {
        return timerBeans;
    }

    public void setTimerBeans(List<TimerBean> timerBeans) {
        this.timerBeans = timerBeans;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
