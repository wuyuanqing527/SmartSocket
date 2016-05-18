package com.wuyuanqing.smartsocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.constant.SmartSocketUrl;
import com.wuyuanqing.smartsocket.model.ResTimer;
import com.wuyuanqing.smartsocket.model.TimerBean;
import com.wuyuanqing.smartsocket.util.JsonStringUtil;
import com.wuyuanqing.smartsocket.util.OkHttpClientManager;
import com.wuyuanqing.smartsocket.util.TimerListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyq on 2016/5/10.
 */
public class TimerManageActivity extends BaseActivity {

    private Button addBt, backBt;
    private ListView timerList;
    private List<TimerBean> timerBeanList;
    private String socketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_manager);
        addBt = (Button) findViewById(R.id.timer_manager_add_bt);
        backBt = (Button) findViewById(R.id.timer_manager_back_bt);
        timerList = (ListView) findViewById(R.id.timer_manager_listview);

        socketName = getIntent().getStringExtra("socketName");
       // l(socketName);
        timerBeanList = new ArrayList<>();
//        queryTimer();
//
//        // TimerBean timerBean = (TimerBean) getIntent().getSerializableExtra("timerBean");
//        if (timerBeanList.size() > 0) {
//            TimerListAdapter adapter = new TimerListAdapter(this, timerBeanList);
//            timerList.setAdapter(adapter);
//        }
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.putExtra("socketName",socketName);
                intent.setClass(TimerManageActivity.this, TimerSetActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void queryTimer() {

        Log.i("queryTimer", JsonStringUtil.queryTimer(socketName));
        OkHttpClientManager.postAsyn(SmartSocketUrl.timerUrl, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                l("访问服务器出现问题");
            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    l("服务器返回值为空");
                } else {
                    l(response);
                    ResTimer resTimer = new Gson().fromJson(response.toString(), ResTimer.class);
                    if (resTimer.getResultCode() == 0) {
                        l("查询所有定时失败");
                    } else {
                        List<TimerBean> timerBeans = resTimer.getTimerBeans();
                        timerBeanList = timerBeans;
                        if (timerBeanList.size() > 0) {
                            TimerListAdapter adapter = new TimerListAdapter(TimerManageActivity.this, timerBeanList);
                            timerList.setAdapter(adapter);
                            timerList.invalidate();
                        }
                    }
                }
            }
        }, new OkHttpClientManager.Param("queryAllTimer", JsonStringUtil.queryTimer(socketName)));

    }


    @Override
    protected void onResume() {
        super.onResume();

        queryTimer();

    }

}
