package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.adapter.AddressListAdapter;
import com.leetai.purchasingagent.adapter.PublishListAdapter;
import com.leetai.purchasingagent.modle.Address;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressListActivity extends Activity {
    private static final int REQUEST_CODE_FROM_ADDRESS = 1001;
    List<Address> list_address = new ArrayList<Address>();
    List<Map<String, Object>> listmap;
    HashMap<String, Object> map;
    @ViewInject(R.id.btn_add)
    Button btn_add;
    AddressListAdapter addressListAdapter;
    @ViewInject(R.id.lv_address)
    ListView lv_address;

    @OnClick(R.id.btn_add)
    public void addClick(View view) {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra("type", "add");
        intent.putExtra("req_code", "req_code");
        startActivityForResult(intent, REQUEST_CODE_FROM_ADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ViewUtils.inject(this);

        getList();
    }

    private void getList() {

        SharedPreferencesTool.get(this, "userId", 0);
        // Log.i("获取userID=",SharedPreferencesTool.get(getActivity(),"userId",0)+"");
        String url = HttpTool.getUrl(new String[]{"query",SharedPreferencesTool.get(this, "userId", 0) + ""}, "AddressServlet");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(100);
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Gson gson = new Gson();
                        list_address = gson.fromJson(responseInfo.result, new TypeToken<List<Address>>() {
                        }.getType());
                        //Log.i("list_address=",list_address.get()+"");
//                        for (int i = 0; i < list_address.size(); i++) {
//                            Log.i("getTitle() =", list_address.get(i).getTitle());
//                        }

                            listmap = new ArrayList<Map<String, Object>>();
                            for (int d = 0; d < list_address.size(); d++) {
                                map = new HashMap<String, Object>();
                                map.put("tv_name", list_address.get(d).getName());
                                map.put("tv_phone", list_address.get(d).getPhone());
                                map.put("tv_region", list_address.get(d).getRegion());
                                map.put("tv_detail", list_address.get(d).getDetail());
                                 map.put("id", list_address.get(d).getId());
                                listmap.add(map);
                            }

                        addressListAdapter = new AddressListAdapter(AddressListActivity.this, listmap, list_address,AddressListActivity.this);
                        lv_address.setAdapter(addressListAdapter);

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(AddressListActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });

    }

}
