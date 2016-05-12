package com.wuyuanqing.smartsocket.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wuyuanqing.smartsocket.R;

/**
 * Created by wyq on 2016/5/10.
 */
public class TimerSetActivity extends BaseActivity {

    private TimePicker timePicker;
    private Button BackBt,confirmBt;
    private TextView startTimeText, endTimeText;
    private TextView startTime, endTime;
    private LinearLayout startLayout, endLayout;
    private int hour, minute;
    private boolean start = true, end = false,confirm=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initView();

        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
    }

    private void initView() {

        BackBt=(Button)findViewById(R.id.timer_back_bt);
        confirmBt=(Button)findViewById(R.id.timer_confirm_bt);

        timePicker = (TimePicker) findViewById(R.id.timer_timepicker);
        startLayout = (LinearLayout) findViewById(R.id.timer_start_time_layout);
        startTimeText = (TextView) findViewById(R.id.timer_start_time_text);
        startTimeText.setTextColor(getResources().getColor(R.color.light_blue));
        startTime = (TextView) findViewById(R.id.timer_start_time);
        endLayout = (LinearLayout) findViewById(R.id.timer_end_time_layout);
        endTimeText = (TextView) findViewById(R.id.timer_end_time_text);
        endTime = (TextView) findViewById(R.id.timer_end_time);

        BackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimerSetActivity.this,TimerManageActivity.class));
                finish();
            }
        });

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!confirm){
                    confirmBt.setBackgroundResource(R.drawable.confirm_green);
                    confirm=true;
                    Intent intent=new Intent();
                    intent.putExtra("startTime",startTime.getText().equals("")?"":startTime.getText());
                    intent.putExtra("endTime",endTime.getText().equals("")?"":endTime.getText());
                    intent.setClass(TimerSetActivity.this,TimerManageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                l(hourOfDay + ":" + minute);
                if (start) {
                    startTime.setText(hourOfDay + ":" + minute);
                }

                if (end) {
                    endTime.setText(hourOfDay + ":" + minute);
                }
            }
        });

        startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = true;
                end = false;
                startTimeText.setTextColor(getResources().getColor(R.color.light_blue));
                endTimeText.setTextColor(getResources().getColor(R.color.black));
            }
        });

        endLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = false;
                end = true;
                startTimeText.setTextColor(getResources().getColor(R.color.black));
                endTimeText.setTextColor(getResources().getColor(R.color.light_blue));
            }
        });

    }
}
