package com.wuyuanqing.smartsocket.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.widget.LineChartView;

/**
 * Created by Uni.W on 2016/5/10.
 */
public class HistoryActivity extends BaseActivity {

    private Button backBt;
    private LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lineChartView=(LineChartView)findViewById(R.id.history_weekchart);
        lineChartView.setPowerLimit(powerLimitNum);
        l(""+powerLimitNum);
        backBt = (Button) findViewById(R.id.history_back_bt);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
