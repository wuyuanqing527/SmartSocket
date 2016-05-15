package com.wuyuanqing.smartsocket.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuyuanqing.smartsocket.R;

/**
 * Created by Uni.W on 2016/5/10.
 */
public class HistoryActivity extends BaseActivity {

    private Button backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        backBt = (Button) findViewById(R.id.history_back_bt);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
