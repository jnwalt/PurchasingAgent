package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Order;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.leetai.purchasingagent.tools.Tools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class OrderActivity extends Activity {
    @ViewInject(R.id.tv_p_title)
    TextView tv_p_title;
    @ViewInject(R.id.tv_p_description)
    TextView tv_p_description;
    @ViewInject(R.id.tv_p_address)
    TextView tv_p_address;
    @ViewInject(R.id.tv_p_price)
    TextView tv_p_price;
    @ViewInject(R.id.tv_p_addtime)
    TextView tv_p_addtime;
    @ViewInject(R.id.tv_s_user)
    TextView tv_s_user;
    @ViewInject(R.id.tv_s_price)
    TextView tv_s_price;
    @ViewInject(R.id.tv_s_address)
    TextView tv_s_address;
    @ViewInject(R.id.tv_s_addtime)
    TextView tv_s_addtime;

    String type, s_id = "";
    Order orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ViewUtils.inject(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("0")) {
            s_id = intent.getStringExtra("sId");

            String url = HttpTool.getUrl(new String[]{Tools.ACTION_TYPE_QUERY_DETAIL, s_id}, "OrderServlet");
            HttpUtils http = new HttpUtils();
            http.configCurrentHttpCacheExpiry(100);
            http.send(HttpRequest.HttpMethod.GET, url,
                    new RequestCallBack<String>() {

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            Gson gson = new Gson();
                            orderDetail = gson.fromJson(responseInfo.result, Order.class);
                            setTextView();
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            ToastTool.showToast(OrderActivity.this, "连接失败，请检查网络连接！！！");
                        }
                    });
        }
    }

    private void setTextView() {
        tv_p_title.setText(orderDetail.getBid().getPublish().getpTitle());
        tv_p_description.setText(orderDetail.getBid().getPublish().getpDescription());
        tv_p_address.setText(orderDetail.getBid().getPublish().getpAddress());
        tv_p_price.setText(orderDetail.getBid().getPublish().getpPrice().toString());
        tv_p_addtime.setText(orderDetail.getBid().getPublish().getpAddTime().toString());
        tv_s_user.setText(orderDetail.getBid().getsUser().getUsername());
        tv_s_price.setText(orderDetail.getBid().getsPrice().toString());
        tv_s_address.setText(orderDetail.getBid().getsAddress());
        tv_s_addtime.setText(orderDetail.getBid().getsAddTime().toString());
    }


}
