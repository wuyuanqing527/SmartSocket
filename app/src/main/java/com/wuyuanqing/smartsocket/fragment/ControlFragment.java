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

import java.text.DecimalFormat;

/**
 * Created by wuyuanqing on 2015/8/28.
 */
public class ControlFragment extends BaseFragment {

    private TextView currentTV, ratedCurrentTV, voltageTV, ratedVoltageTV, powerTV, ratedPowerTV, workTimeTV, energyTV;
    private Button onOffBt;
    private String response, socketName;
    private boolean ISOPEN = false;//插座是否打开

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
        ratedCurrentTV.setText(SocketConstant.RATED_CURRENT);
        ratedVoltageTV.setText(SocketConstant.RATED_VOLTAGE);
        ratedPowerTV.setText(SocketConstant.RATED_POWER);

        ResSocketLsit socketLsit = new Gson().fromJson(response, ResSocketLsit.class);
        for (Socketer socketer : socketLsit.getSocketers()) {
            if (socketer.getSocketName().equals(socketName)) {
                currentTV.setText(decimalFormat(socketer.getCurrent()) + "A");
                voltageTV.setText(decimalFormat(socketer.getVoltage()) + "V");
                powerTV.setText(decimalFormat(socketer.getCurrent() * socketer.getVoltage()) + "W");
                workTimeTV.setText(decimalFormat(socketer.getWorkTime()) + "S");
                energyTV.setText(decimalFormat(socketer.getWorkTime() * socketer.getPower() / 3600000) + "KW.h");
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
        currentTV = (TextView) view.findViewById(R.id.control_current);
        ratedCurrentTV = (TextView) view.findViewById(R.id.control_rated_current);
        voltageTV = (TextView) view.findViewById(R.id.control_voltage);
        ratedVoltageTV = (TextView) view.findViewById(R.id.control_rated_voltage);
        powerTV = (TextView) view.findViewById(R.id.control_power);
        ratedPowerTV = (TextView) view.findViewById(R.id.control_rated_power);
        workTimeTV = (TextView) view.findViewById(R.id.control_work_time);
        energyTV = (TextView) view.findViewById(R.id.control_energy);

        onOffBt = (Button) view.findViewById(R.id.control_onoff_bt);
    }


    public String decimalFormat(float x) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        return decimalFormat.format(x);
    }
}
