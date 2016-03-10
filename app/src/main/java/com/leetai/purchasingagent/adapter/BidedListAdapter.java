package com.leetai.purchasingagent.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.OrderActivity;
import com.leetai.purchasingagent.activity.PublishActivity;
import com.leetai.purchasingagent.modle.Bid;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.BitMapTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.leetai.purchasingagent.tools.Tools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
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
ImageView iv_head;

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
        iv_head= (ImageView) view.findViewById(R.id.iv_head);

        tv_username.setText(getItem(position).get("tv_username").toString());
        tv_price.setText(getItem(position).get("tv_price").toString());
        tv_s_address.setText(getItem(position).get("tv_s_address").toString());

        BitmapUtils bitmapUtils = BitMapTool.getBitmapUtils(context);
        String path  = Tools.getUserHeadURL(Tools.getUserId(context)) ;
        bitmapUtils.display(iv_head, path, new CustomBitmapLoadCallBack());




        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_id = (int) getItem(position).get("s_id");
                String url = HttpTool.getUrl(s_id + "", "MatchingServlet");
                HttpUtils http = new HttpUtils();
                http.configCurrentHttpCacheExpiry(100);
                http.send(HttpRequest.HttpMethod.GET, url,
                        new RequestCallBack<String>() {

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                ToastTool.showToast(context, "成功！");
                                Intent intent = new Intent(context, OrderActivity.class);
                                intent.putExtra("type","0");
                                intent.putExtra("sId",s_id+"");
                                context.startActivity(intent);
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

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            bitmap = BitMapTool.compressQualityImage(bitmap);
            bitmap = BitMapTool.cutToRound(bitmap);
            container.setImageBitmap(bitmap);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            super.onLoadFailed(container, uri, drawable);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaulthad);
            bitmap = BitMapTool.cutToRound(bitmap);
            container.setImageBitmap(bitmap);
        }
    }
}
