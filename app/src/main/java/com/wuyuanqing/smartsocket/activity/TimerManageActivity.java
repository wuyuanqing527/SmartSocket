package com.wuyuanqing.smartsocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.model.TimerBean;
import com.wuyuanqing.smartsocket.util.TimerListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyq on 2016/5/10.
 */
public class TimerManageActivity extends BaseActivity {

    private Button addBt,backBt;
    private ListView timerList;
    private List<TimerBean> timerBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_manager);
        addBt=(Button)findViewById(R.id.timer_manager_add_bt);
        backBt=(Button)findViewById(R.id.timer_manager_back_bt);
        timerList=(ListView)findViewById(R.id.timer_manager_listview);

        timerBeanList=new ArrayList<>();
        TimerListAdapter adapter=new TimerListAdapter(this,timerBeanList);
        timerList.setAdapter(adapter);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimerManageActivity.this,TimerSetActivity.class));
                finish();
            }
        });
    }
}
