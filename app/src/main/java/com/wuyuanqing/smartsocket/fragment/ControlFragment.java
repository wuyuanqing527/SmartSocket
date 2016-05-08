package com.wuyuanqing.smartsocket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.constant.SocketConstant;
import com.wuyuanqing.smartsocket.model.ResSocketLsit;
import com.wuyuanqing.smartsocket.model.Socketer;
import com.wuyuanqing.smartsocket.widget.PercentBar;

import java.text.DecimalFormat;

/**
 * Created by wuyuanqing on 2015/8/28.
 */
public class ControlFragment extends BaseFragment {

   // private TextView currentTV, ratedCurrentTV, voltageTV, ratedVoltageTV, powerTV, ratedPowerTV, workTimeTV, energyTV;
    private Button onOffBt;
    private String response, socketName;
    private boolean ISOPEN = false;//插座是否打开
    private PercentBar currentBar,voltageBar,powerBar,workTimeBar,energyBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control, container, false);

        response = getArguments().getString("response");
        socketName = getArguments().getString("socketName");
        initView(view);
        setViewData();
        return view;
    }

    private void setViewData() {
        ResSocketLsit socketLsit = new Gson().fromJson(response, ResSocketLsit.class);
        for (Socketer socketer : socketLsit.getSocketers()) {
            if (socketer.getSocketName().equals(socketName)) {
                currentBar.setPara("电流",SocketConstant.rated_current,0,socketer.getCurrent());
                voltageBar.setPara("电压",SocketConstant.rated_voltage,0,socketer.getVoltage());
                powerBar.setPara("功率",SocketConstant.rated_power,0,socketer.getPower());
                workTimeBar.setPara("工作时间",24,0,socketer.getWorkTime());
                energyBar.setPara("电能",100,0,socketer.getEnergy());
            }
        }
        onOffBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ISOPEN) {
                    onOffBt.setBackgroundResource(R.drawable.toggle_grey);
                    ISOPEN=false;
                } else {
                    onOffBt.setBackgroundResource(R.drawable.toggle_green);
                    ISOPEN=true;
                }
            }
        });

    }

    private void initView(View view) {
        onOffBt = (Button) view.findViewById(R.id.control_onoff_bt);

        currentBar=(PercentBar)view.findViewById(R.id.current_bar);
        voltageBar=(PercentBar)view.findViewById(R.id.voltage_bar);
        powerBar=(PercentBar)view.findViewById(R.id.power_bar);
        workTimeBar=(PercentBar)view.findViewById(R.id.worktime_bar);
        energyBar=(PercentBar)view.findViewById(R.id.energy_bar);
    }


    public String decimalFormat(float x) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        return decimalFormat.format(x);
    }
}
