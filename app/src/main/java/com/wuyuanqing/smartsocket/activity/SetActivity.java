package com.wuyuanqing.smartsocket.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuyuanqing.smartsocket.R;

/**
 * Created by wyq on 2016/5/15.
 */
public class SetActivity extends BaseActivity {

    private Button backBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        backBt=(Button)findViewById(R.id.activity_set_bt_login_back);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
