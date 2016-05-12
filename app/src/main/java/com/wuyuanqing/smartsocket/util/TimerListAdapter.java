package com.wuyuanqing.smartsocket.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.model.TimerBean;

import java.util.List;

/**
 * Created by wyq on 2016/5/11.
 */
public class TimerListAdapter extends BaseAdapter {

    private List<TimerBean> timerList = null;
    private Context context;

    public TimerListAdapter(Context context, List<TimerBean> timerList) {
        this.timerList = timerList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return timerList.size();
    }

    @Override
    public Object getItem(int position) {
        return timerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.timer_manager_list_item, null);
            holder.timeText = (TextView) convertView.findViewById(R.id.timer_manager_list_text);
            holder.timerSwitch = (Switch) convertView.findViewById(R.id.timer_manager_list_switch);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String startTime = timerList.get(position).getStartTime();
        String endTime = timerList.get(position).getEndTime();
        holder.timeText.setText(endTime.equals("") ? "开启时间：" + startTime : "开启时段：" + startTime + "——" + endTime);
        holder.timerSwitch.setChecked(timerList.get(position).isOnOff());
        return convertView;
    }

    class ViewHolder {
        TextView timeText;
        Switch timerSwitch;
    }
}
