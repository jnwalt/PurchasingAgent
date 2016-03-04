package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Order;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class OrderActivity extends Activity {
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_description)
    TextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Order order = new Order();
        order = (Order) bundle.getSerializable("order");
        tv_title.setText(order.getBid().getPublish().getpTitle());
        tv_description.setText(order.getBid().getPublish().getpDescription());
    }


}
