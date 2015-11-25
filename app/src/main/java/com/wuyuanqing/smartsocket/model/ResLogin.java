package com.wuyuanqing.smartsocket.model;

import java.util.Date;

/**
 * Created by wyq on 2015/11/23.
 */
public class ResLogin {
    private User user;
    /**
     * 0:登录成功
     * 1：密码错误
     * 2：用户不存在
     */
    private int errorCode;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
