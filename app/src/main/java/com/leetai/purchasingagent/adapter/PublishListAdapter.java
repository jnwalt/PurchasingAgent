package com.leetai.purchasingagent.adapter;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.PublishActivity;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.BitMapTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016-02-18.
 */
public class PublishListAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> list;
    String id, user_id;
    List<Publish> list_publish;
    Fragment fragment;

    public PublishListAdapter(Context context, List<Map<String, Object>> list, List<Publish> list_publish, final Fragment fragment) {
        this.context = context;
        this.list = list;
        this.list_publish = list_publish;
        this.fragment = fragment;
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

        try {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                  convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_list, null);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
                viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                viewHolder.btn_remove = (Button) convertView.findViewById(R.id.btn_remove);
                viewHolder.btn_modify = (Button) convertView.findViewById(R.id.btn_modify);
                viewHolder.iv_first = (ImageView) convertView.findViewById(R.id.iv_first);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            id = getItem(position).get("id").toString();
            user_id = getItem(position).get("user_id").toString();
            viewHolder.tv_title.setText(getItem(position).get("tv_title").toString());
            viewHolder.tv_description.setText(getItem(position).get("tv_description").toString());
            viewHolder.tv_price.setText(getItem(position).get("tv_price").toString());
            BitmapUtils bitmapUtils = BitMapTool.getBitmapUtils(context);
            String path = HttpTool.getPicUrl() + "/ps/p/" + id + "/" + user_id + "/0.jpg";
            bitmapUtils.display(viewHolder.iv_first, path, new CustomBitmapLoadCallBack());

            viewHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  SharedPreferencesTool.get(getActivity(), "userId", 0);
                    // Log.i("获取userID=",SharedPreferencesTool.get(getActivity(),"userId",0)+"");
                    String url = HttpTool.getUrl(new String[]{"delete", getItem(position).get("id").toString()}, "PublishServlet");
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
            viewHolder.btn_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PublishActivity.class);
                    Bundle bundle = new Bundle();
                    // bundle.putSerializable("publish",getItemId(position));
                    //    System.out.println("publish.getID" + getListItem(position).getId());
                    bundle.putSerializable("publish", getListItem(position));
                    intent.putExtra("type", "modify");
                    intent.putExtra("bundle", bundle);
                    // intent.putExtra("id", getItem(position).get("id").toString());

                    fragment.startActivityForResult(intent, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public Publish getListItem(int position) {
        return this.list_publish.get(position);
    }

    public class ViewHolder

    {
        TextView tv_title;
        TextView tv_description;
        TextView tv_price;
        Button btn_remove;
        Button btn_modify;
        ImageView iv_first;
    }

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            bitmap = BitMapTool.compressQualityImage(bitmap);
            bitmap = BitMapTool.cutToCrop(bitmap);
            container.setImageBitmap(bitmap);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            super.onLoadFailed(container, uri, drawable);
            container.setImageResource(R.drawable.img_add);
        }
    }
}
