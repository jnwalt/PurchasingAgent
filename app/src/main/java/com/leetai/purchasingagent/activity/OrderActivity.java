package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Order;
import com.leetai.purchasingagent.tools.BitMapTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.leetai.purchasingagent.tools.Tools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
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
    @ViewInject(R.id.tv_s_memo)
    TextView tv_s_memo;
    @ViewInject(R.id.iv_head)
    ImageView iv_head;

    String type, s_id = "";
    Order orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ViewUtils.inject(this);
        Tools.initTitleView(OrderActivity.this, getWindow(), R.string.title_activity_order_info, R.string.title_activity_order_info, null, true);
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
        }else  if (type.equals("1")) {

            orderDetail = (Order) intent.getBundleExtra("bundle").getSerializable("order");
            setTextView();
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
        tv_s_memo.setText(orderDetail.getBid().getsMemo());

        BitmapUtils bitmapUtils = BitMapTool.getBitmapUtils(OrderActivity.this);
        String path = Tools.getUserHeadURL(orderDetail.getBid().getsUser().getUserId());
        bitmapUtils.display(iv_head, path, new CustomBitmapLoadCallBack());



    }

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            bitmap = BitMapTool.cutToRound(bitmap);
            container.setImageBitmap(bitmap);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            super.onLoadFailed(container, uri, drawable);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaulthad);
            bitmap = BitMapTool.cutToRound(bitmap);
            container.setImageBitmap(bitmap);
        }
    }

}
