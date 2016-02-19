package com.leetai.purchasingagent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leetai.purchasingagent.R;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016-02-18.
 */
public class PublishListAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> list;
    TextView tv_title;
    TextView tv_description;
    TextView tv_price;

    public PublishListAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_publish_list, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_price = (TextView) view.findViewById(R.id.tv_price);

        tv_title.setText(getItem(position).get("tv_title").toString());
        tv_description.setText(getItem(position).get("tv_description").toString());
        tv_price.setText(getItem(position).get("tv_price").toString());

        return view;
    }
}
