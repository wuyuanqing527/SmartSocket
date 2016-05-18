package com.wuyuanqing.smartsocket.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.constant.SmartSocketUrl;
import com.wuyuanqing.smartsocket.model.ResTimer;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

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

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("确认要删除嘛？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除服务器
                        OkHttpClientManager.postAsyn(SmartSocketUrl.timerUrl, new OkHttpClientManager.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                l("访问服务器出现问题");
                            }

                            @Override
                            public void onResponse(String response) {
                                if (response == null) {
                                    l("服务器返回值为空");
                                } else {
                                    l(response);
                                    ResTimer resTimer = new Gson().fromJson(response.toString(), ResTimer.class);
                                    if (resTimer.getResultCode() == 0) {
                                        l("查询所有定时失败");
                                    } else {
                                        l("删除成功!");
                                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }, new OkHttpClientManager.Param("deleteTimer", JsonStringUtil.deteleTimer(timerList.get(position))));
                        timerList.remove(position);
                        notifyDataSetInvalidated();
                    }
                });

                builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });

        return convertView;
    }
    public void l(String s){
        Log.i("activity", s);
    }
    class ViewHolder {
        TextView timeText;
        Switch timerSwitch;
    }
}
