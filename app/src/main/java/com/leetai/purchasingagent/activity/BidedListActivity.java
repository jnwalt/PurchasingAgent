package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.adapter.BidedListAdapter;
import com.leetai.purchasingagent.adapter.PublishListAdapter;
import com.leetai.purchasingagent.modle.Bid;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidedListActivity extends Activity {
    @ViewInject(R.id.lv_bided)
    ListView lv_bided;
    int p_id;
    List<Map<String, Object>> listmap;
    HashMap<String, Object> map;
BidedListAdapter bidedListAdapter;
    List<Bid> list_bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bided_list);
        ViewUtils.inject(this);
        Intent intent =getIntent();
        p_id = intent.getIntExtra("p_id",0);
        getList();
    }

    private void getList() {

        SharedPreferencesTool.get(BidedListActivity.this, "userId", 0);
        // Log.i("获取userID=",SharedPreferencesTool.get(BidedListActivity.this,"userId",0)+"");
        String url = HttpTool.getUrl( p_id + "", "PBidedServlet");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(100);
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Gson gson = new Gson();
                        list_bid = gson.fromJson(responseInfo.result, new TypeToken<List<Bid>>() {
                        }.getType());

                        listmap = new ArrayList<Map<String, Object>>();
                        for (int d = 0; d < list_bid.size(); d++) {
                            map = new HashMap<String, Object>();
                          //  map.put("tv_username", list_bid.get(d).getTitle());
                            map.put("tv_price", list_bid.get(d).getsPrice());
                            map.put("tv_s_address", list_bid.get(d).getsAddress());

                            map.put("s_id", list_bid.get(d).getsId());
                            listmap.add(map);
                        }
                        bidedListAdapter = new BidedListAdapter(BidedListActivity.this, listmap, list_bid );
                        lv_bided.setAdapter(bidedListAdapter);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(BidedListActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });

    }

}
