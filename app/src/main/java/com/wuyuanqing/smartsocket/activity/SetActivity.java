package com.wuyuanqing.smartsocket.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wuyuanqing.smartsocket.R;

/**
 * Created by wyq on 2016/5/15.
 */
public class SetActivity extends BaseActivity {

    private Button backBt;
    private Button okBt;
    private EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        okBt=(Button)findViewById(R.id.set_ok);
        edit=(EditText)findViewById(R.id.set_edit);
        backBt=(Button)findViewById(R.id.activity_set_bt_login_back);

        edit.setText(powerLimitNum+"");

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                powerLimitNum=edit.getText().toString().equals("")?5:Double.parseDouble(edit.getText().toString());
                finish();
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
