package com.leetai.purchasingagent.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Publish;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016-02-23.
 */
public class RegionListAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> list;
    TextView tv_txt;

    public RegionListAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_region_list, null);

        tv_txt = (TextView) view.findViewById(R.id.tv_txt);
        tv_txt.setText(getItem(position).get("tv_txt").toString());
        return view;
    }
}
