package com.wuyuanqing.smartsocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.constant.SmartSocketUrl;
import com.wuyuanqing.smartsocket.model.ResTimer;
import com.wuyuanqing.smartsocket.model.TimerBean;
import com.wuyuanqing.smartsocket.util.JsonStringUtil;
import com.wuyuanqing.smartsocket.util.OkHttpClientManager;

/**
 * Created by wyq on 2016/5/10.
 */
public class TimerSetActivity extends BaseActivity {

    private TimePicker timePicker;
    private Button BackBt, confirmBt;
    private TextView startTimeText, endTimeText;
    private TextView startTime, endTime;
    private LinearLayout startLayout, endLayout;
    private int hour, minute;
    private boolean start = true, end = false, confirm = false;
    private String socketName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        socketName = getIntent().getStringExtra("socketName");

        initView();

        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
    }

    private void initView() {

        BackBt = (Button) findViewById(R.id.timer_back_bt);
        confirmBt = (Button) findViewById(R.id.timer_confirm_bt);

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
                finish();
            }
        });

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!confirm) {

                    Intent intent = new Intent();
                    TimerBean timerBean = new TimerBean();
                    timerBean.setSocketName(socketName);
                    timerBean.setStartTime(startTime.getText().equals("") ? "" : startTime.getText().toString());
                    timerBean.setEndTime(endTime.getText().equals("") ? "" : endTime.getText().toString());
                    timerBean.setOnOff(true);
                    if ((!timerBean.getStartTime().equals("")) || (!timerBean.getEndTime().equals(""))) {
//                        confirmBt.setBackgroundResource(R.drawable.confirm_green);
//                        confirm=true;
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("timerBean", timerBean);
//                        intent.putExtras(bundle);
//                        setResult(1, intent);
                        addTimer(timerBean);
                        // finish();
                    } else {
                        Toast.makeText(TimerSetActivity.this, "您未设置时间！", Toast.LENGTH_SHORT).show();
                    }

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

    private void addTimer(TimerBean timerBean) {
        OkHttpClientManager.postAsyn(SmartSocketUrl.timerUrl, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                l("访问服务器出现问题");
            }

            @Override
            public void onResponse(String response) {
                confirmBt.setBackgroundResource(R.drawable.confirm_green);
                confirm = true;
                ResTimer resTimer = new Gson().fromJson(response.toString(), ResTimer.class);
                if (resTimer.getResultCode() == 0) {
                    l("查询所有定时失败");
                } else {
                    l("添加定时的成功");
                    finish();
                }
            }
        }, new OkHttpClientManager.Param("queryAllTimer", JsonStringUtil.addTimer(timerBean)));
    }
}
