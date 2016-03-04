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

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.PublishActivity;
import com.leetai.purchasingagent.modle.Order;
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
 * Created by dell on 2016-03-04.
 */
public class OrderListAdapter extends BaseAdapter{
    Context context;
    List<Map<String, Object>> list;
    TextView tv_title;
    TextView tv_description;
    TextView tv_price;
    Button btn_remove;
    Button btn_modify;
    List<Order> list_order;
    Fragment fragment;

    public OrderListAdapter(Context context, List<Map<String, Object>> list, List<Order> list_order ) {
        this.context = context;
        this.list = list;
        this.list_order = list_order;

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

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order_list, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
      //  tv_price = (TextView) view.findViewById(R.id.tv_price);
//
//        btn_remove = (Button) view.findViewById(R.id.btn_remove);
//        btn_modify = (Button) view.findViewById(R.id.btn_modify);

        tv_title.setText(getItem(position).get("tv_title").toString());
        tv_description.setText(getItem(position).get("tv_description").toString());
     //   tv_price.setText(getItem(position).get("tv_price").toString());

//
//        btn_remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  SharedPreferencesTool.get(getActivity(), "userId", 0);
//                // Log.i("获取userID=",SharedPreferencesTool.get(getActivity(),"userId",0)+"");
//                String url = HttpTool.getUrl(new String[]{"delete", getItem(position).get("id").toString()}, "PublishServlet");
//                HttpUtils http = new HttpUtils();
//                http.configCurrentHttpCacheExpiry(100);
//                http.send(HttpRequest.HttpMethod.GET, url,
//                        new RequestCallBack<String>() {
//
//                            @Override
//                            public void onSuccess(ResponseInfo<String> responseInfo) {
//                                list.remove(position);
//                                notifyDataSetChanged();
//
//                            }
//
//                            @Override
//                            public void onFailure(HttpException error, String msg) {
//                                ToastTool.showToast(context, "连接失败，请检查网络连接！！！");
//
//                            }
//                        });
//            }
//        });
//        btn_modify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PublishActivity.class);
//                Bundle bundle = new Bundle();
//                // bundle.putSerializable("publish",getItemId(position));
//                //    System.out.println("publish.getID" + getListItem(position).getId());
//                bundle.putSerializable("publish", getListItem(position));
//                intent.putExtra("type", "modify");
//                intent.putExtra("bundle", bundle);
//                // intent.putExtra("id", getItem(position).get("id").toString());
//
//                fragment.startActivityForResult(intent, 0);
//            }
//        });
        return view;
    }

    public Order getListItem(int position) {
        return this.list_order.get(position);
    }
}
