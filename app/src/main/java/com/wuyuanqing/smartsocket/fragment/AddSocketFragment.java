package com.wuyuanqing.smartsocket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuyuanqing.smartsocket.R;

/**
 * Created by wyq on 2015/11/11.
 */
public class AddSocketFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addsocket,container,false);
        return view;
    }
}
