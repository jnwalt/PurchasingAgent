package com.leetai.purchasingagent.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.PublishActivity;
import com.leetai.purchasingagent.modle.Bid;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016-03-02.
 */
public class BidedListAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> list;

    TextView tv_username;
    TextView tv_s_address;
    TextView tv_price;
    Button btn_choose;

    List<Bid> list_bided;
    Fragment fragment;
    int p_id, s_id;

    public BidedListAdapter(Context context, List<Map<String, Object>> list, List<Bid> list_bided) {
        this.context = context;
        this.list = list;
        this.list_bided = list_bided;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_bided_list, null);
        tv_s_address = (TextView) view.findViewById(R.id.tv_s_address);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        btn_choose = (Button) view.findViewById(R.id.btn_choose);

        // tv_username.setText(getItem(position).get("tv_username").toString());
        tv_price.setText(getItem(position).get("tv_price").toString());
        tv_s_address.setText(getItem(position).get("tv_s_address").toString());

        s_id = (int) getItem(position).get("s_id");
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = HttpTool.getUrl(s_id + "", "MatchingServlet");
                HttpUtils http = new HttpUtils();
                //http.configCurrentHttpCacheExpiry(100);
                http.send(HttpRequest.HttpMethod.GET, url,
                        new RequestCallBack<String>() {

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                ToastTool.showToast(context, "成功！");
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                ToastTool.showToast(context, "连接失败，请检查网络连接！！！");
                            }
                        });


            }
        });
        return view;
    }

    public Bid getListItem(int position) {
        return this.list_bided.get(position);
    }
}
