package com.wuyuanqing.smartsocket.util;

import com.google.gson.Gson;
import com.wuyuanqing.smartsocket.model.ReqLogin;
import com.wuyuanqing.smartsocket.model.ReqSocketLsit;
import com.wuyuanqing.smartsocket.model.User;

/**
 * Created by wyq on 2015/11/19.
 */
public class JsonStringUtil {

    public static String getSocketList(String username){

        ReqSocketLsit reqSocketLsit=new ReqSocketLsit();
        reqSocketLsit.setUsername(username);
        return new Gson().toJson(reqSocketLsit);
    }

    public static String loginStr(String username,String password){
        User user=new User();
        user.setUserName(username);
        user.setPassWord(password);
        ReqLogin reqLogin=new ReqLogin();
        reqLogin.setUser(user);
        return new Gson().toJson(reqLogin);
    }
}
