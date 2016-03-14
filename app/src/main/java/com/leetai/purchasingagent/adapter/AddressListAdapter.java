package com.leetai.purchasingagent.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.AddressActivity;
import com.leetai.purchasingagent.activity.AddressListActivity;
import com.leetai.purchasingagent.activity.PublishActivity;
import com.leetai.purchasingagent.modle.Address;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016-02-22.
 */
public class AddressListAdapter extends BaseAdapter {
    TextView tv_name;
    TextView tv_phone;
    TextView tv_region;
    TextView tv_detail;
    CheckBox cb_default;
    Context context;
    List<Map<String, Object>> list;
    List<Address> list_address;

    Button btn_modify;
    Button btn_remove;
    Activity activity;

    public AddressListAdapter(Context context, List<Map<String, Object>> list, List<Address> list_address, AddressListActivity activity) {
        this.context = context;
        this.list = list;
        this.list_address = list_address;
        this.activity = activity;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_address_list, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_region = (TextView) view.findViewById(R.id.tv_region);
        tv_detail = (TextView) view.findViewById(R.id.tv_detail);
        btn_modify = (Button) view.findViewById(R.id.btn_modify);
        btn_remove = (Button) view.findViewById(R.id.btn_remove);
        cb_default = (CheckBox) view.findViewById(R.id.cb_default);

        tv_name.setText(list.get(position).get("tv_name").toString());
        tv_phone.setText(list.get(position).get("tv_phone").toString());
        tv_region.setText(list.get(position).get("tv_region").toString());
        tv_detail.setText(list.get(position).get("tv_detail").toString());
        if ((int) list.get(position).get("cb_default") == 0) {
            cb_default.setChecked(false);
        } else {
            cb_default.setChecked(true);
        }

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  SharedPreferencesTool.get(getActivity(), "userId", 0);
                // Log.i("获取userID=",SharedPreferencesTool.get(getActivity(),"userId",0)+"");
                String url = HttpTool.getUrl(new String[]{"delete", getItem(position).get("id").toString()}, "AddressServlet");
                HttpUtils http = new HttpUtils();
                http.configCurrentHttpCacheExpiry(100);
                http.send(HttpRequest.HttpMethod.GET, url,
                        new RequestCallBack<String>() {

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                list.remove(position);
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                ToastTool.showToast(context, "连接失败，请检查网络连接！！！");

                            }
                        });
            }
        });
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddressActivity.class);
                Bundle bundle = new Bundle();
                // bundle.putSerializable("publish",getItemId(position));
                //    System.out.println("publish.getID" + getListItem(position).getId());
                bundle.putSerializable("address", getListItem(position));
                intent.putExtra("type", "modify");
                intent.putExtra("bundle", bundle);
                // intent.putExtra("id", getItem(position).get("id").toString());
                // context.startActivityForResult(intent, 0);
                activity.startActivityForResult(intent, 0);
            }
        });
        return view;
    }

    public Address getListItem(int position) {
        return this.list_address.get(position);
    }
}
