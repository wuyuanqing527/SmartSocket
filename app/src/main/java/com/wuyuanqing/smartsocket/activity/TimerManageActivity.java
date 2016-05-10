package com.wuyuanqing.smartsocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuyuanqing.smartsocket.R;

/**
 * Created by wyq on 2016/5/10.
 */
public class TimerManageActivity extends BaseActivity {

    private Button addBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_manager);
        addBt=(Button)findViewById(R.id.timer_manager_add_bt);

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimerManageActivity.this,TimerSetActivity.class));
                finish();
            }
        });
    }
}
