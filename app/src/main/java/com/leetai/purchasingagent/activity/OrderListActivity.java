package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.adapter.BidedListAdapter;
import com.leetai.purchasingagent.adapter.OrderListAdapter;
import com.leetai.purchasingagent.adapter.PublishListAdapter;
import com.leetai.purchasingagent.modle.Bid;
import com.leetai.purchasingagent.modle.Order;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.modle.Type;
import com.leetai.purchasingagent.tools.GsonTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.leetai.purchasingagent.tools.Tools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListActivity extends Activity {
    @ViewInject(R.id.lv_order)
    ListView lv_order;
    List<Map<String, Object>> listmap;
    HashMap<String, Object> map;
    OrderListAdapter orderListAdapter;
    List<Order> list_order;
    String type, orderType,title;
    int userId;


    @OnItemClick(R.id.lv_order)
    public void orderItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(OrderListActivity.this, OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", list_order.get(position));
        intent.putExtra("bundle", bundle);
        intent.putExtra("type", "1");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ViewUtils.inject(this);
        final Intent intent = getIntent();

        orderType = intent.getStringExtra("orderType");
        type = Type.ACTION_TYPE_QUERY;


        if (orderType.equals("0")) {
            title = "待付款";
        } else if (orderType.equals("1")) {
            title = "待发货";
        } else if (orderType.equals("2")) {
            title = "待收货";
        } else if (orderType.equals("3")) {
            title = "待评价";
        } else {
            title = "全部";
        }
        Tools.initTitleView(OrderListActivity.this, getWindow(), title, title, null, true);


        getList();


    }

    private void getList() {
        userId = (int) SharedPreferencesTool.get(OrderListActivity.this, "userId", 0);
        String url = HttpTool.getUrl(new String[]{type, userId + "", orderType}, "OrderServlet");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(100);
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Gson gson = new Gson();
                        list_order = gson.fromJson(responseInfo.result, new TypeToken<List<Order>>() {
                        }.getType());
                        // list_order = GsonTool.stringToList(responseInfo.result, Order.class);
                        listmap = new ArrayList<Map<String, Object>>();
                        for (int d = 0; d < list_order.size(); d++) {
                            map = new HashMap<String, Object>();

//                            try {//  map.put("tv_username", list_bid.get(d).getTitle());
//                                String aaa = list_order.get(d).getBid().getPublish().getpTitle();
//                                Log.i("aaat=", aaa);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            map.put("tv_title", list_order.get(d).getBid().getPublish().getpTitle());
                            map.put("tv_description", list_order.get(d).getBid().getPublish().getpDescription());
                            map.put("p_id", list_order.get(d).getBid().getPublish().getpId());
                            map.put("user_id", list_order.get(d).getBid().getPublish().getpUser().getUserId());
                            map.put("tv_price", list_order.get(d).getBid().getPublish().getpPrice());
                            listmap.add(map);
                        }
                        orderListAdapter = new OrderListAdapter(OrderListActivity.this, listmap, list_order);
                        lv_order.setAdapter(orderListAdapter);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(OrderListActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });

    }
}
