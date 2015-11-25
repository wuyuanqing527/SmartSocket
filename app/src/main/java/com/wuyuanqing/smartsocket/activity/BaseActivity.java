package com.wuyuanqing.smartsocket.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wyq on 2015/11/23.
 */
public class BaseActivity extends AppCompatActivity {

    public  static boolean ISLOGIN=false;//用户是否登录

    public void l(String s){
        Log.i("activity",s);
    }

    //土司
    private static Toast toast=null;

    public void t(String s) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        } else {
            toast.setText(s);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
