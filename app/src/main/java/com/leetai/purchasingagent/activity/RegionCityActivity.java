package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.adapter.RegionListAdapter;
import com.leetai.purchasingagent.modle.Region;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionCityActivity extends Activity {

    String province, id;
    @ViewInject(R.id.lv_city)
    ListView lv_city;
    RegionListAdapter regionListAdapter;
    List<Region> list_region;
    List<Map<String, Object>> listmap;
    Map<String, Object> map;


    @OnItemClick(R.id.lv_city)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(RegionCityActivity.this, RegionAreaActivity.class);
        intent.putExtra("province", province);
        intent.putExtra("city", regionListAdapter.getItem(position).get("tv_txt").toString());
        intent.putExtra("id", regionListAdapter.getItem(position).get("id").toString());
        startActivity(intent);
      finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_city);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        province = intent.getStringExtra("province");
        id = intent.getStringExtra("id");


        String url = HttpTool.getUrl(new String[]{"query", id}, "RegionServlet");
        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Gson gson = new Gson();
                        list_region = gson.fromJson(responseInfo.result, new TypeToken<List<Region>>() {
                        }.getType());
                       
                        listmap = new ArrayList<Map<String, Object>>();
                        for (int d = 0; d < list_region.size(); d++) {
                            map = new HashMap<String, Object>();
                            map.put("tv_txt", list_region.get(d).getSysRegionName());
                            map.put("id", list_region.get(d).getSysRegionId());
                            listmap.add(map);
                        }

                        regionListAdapter = new RegionListAdapter(RegionCityActivity.this, listmap);
                        lv_city.setAdapter(regionListAdapter);

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(RegionCityActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });

    }

}


