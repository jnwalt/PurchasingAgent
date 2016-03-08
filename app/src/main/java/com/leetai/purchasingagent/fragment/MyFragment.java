package com.leetai.purchasingagent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.OrderListActivity;
import com.leetai.purchasingagent.activity.SettingActivity;
import com.leetai.purchasingagent.activity.UserinfoActivity;
import com.leetai.purchasingagent.tools.BitMapTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ImageTool;
import com.leetai.purchasingagent.tools.MyImgBtn;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

public class MyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyImgBtn MyIBtn1 = null;
    private MyImgBtn MyIBtn2 = null;
    private MyImgBtn MyIBtn3 = null;
    TextView tv_all_order;
    TextView tv_accout;
    TextView tv_setting;
    ImageView iv_head;
    private String mParam1;
    private String mParam2;
    BitmapUtils bitmapUtils ;

    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        bitmapUtils = BitMapTool.getBitmapUtils(getActivity());
        init(view);
        return view;
    }

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            Log.i("onLoadCompleted uri", uri);
            Bitmap bitmap1 = bitmapUtils.getBitmapFromMemCache(uri, config);
            iv_head.setImageBitmap(bitmap1);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            super.onLoadFailed(container, uri, drawable);
            Log.i("onLoadFailed uri", uri);
        }
    }


    private void init(View view) {
        iv_head = (ImageView) view.findViewById(R.id.iv_head);

        tv_accout = (TextView) view.findViewById(R.id.tv_accout);
        tv_setting = (TextView) view.findViewById(R.id.tv_setting);
        tv_all_order = (TextView) view.findViewById(R.id.tv_all_order);
        MyIBtn1 = (MyImgBtn) view.findViewById(R.id.MyImgBtn1);
        MyIBtn1.setImageResource(R.drawable.launcher_bg);
        MyIBtn1.setText("待付款");
        MyIBtn1.setTextSize(13.0f);
        MyIBtn2 = (MyImgBtn) view.findViewById(R.id.MyImgBtn2);
        MyIBtn2.setImageResource(R.drawable.launcher_bg);
        MyIBtn2.setText("待收货");
        MyIBtn2.setTextSize(13.0f);
        MyIBtn3 = (MyImgBtn) view.findViewById(R.id.MyImgBtn3);
        MyIBtn3.setImageResource(R.drawable.launcher_bg);
        MyIBtn3.setText("待评价");
        MyIBtn3.setTextSize(13.0f);
        tv_accout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserinfoActivity.class);
                startActivity(intent);
            }
        });
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        tv_all_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });

        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ImageTool.showImagePickDialogFragment(MyFragment.this);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ImageTool.REQUEST_CODE_FROM_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    String path = ImageTool.getImageAbsolutePath(getActivity(), uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    bitmap = BitMapTool.getSmallBitmap(path);
                   String newPath = BitMapTool.saveBitmapToLocal(bitmap, (int)SharedPreferencesTool.get(getActivity(),"userId",0)+"");
                    bitmap = BitMapTool.cutToCrop(bitmap);
                    iv_head.setImageBitmap(bitmap);
                     saveHeadOnLine(newPath);



                }
                break;
            case ImageTool.REQUEST_CODE_FROM_CAMERA:

                break;
            default:
                break;
        }

    }

    private void saveHeadOnLine( String path) {
        String url = "";
        url = HttpTool.getUrl("", "userHeadServlet");
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("param1", "add");
        requestParams.addBodyParameter("head", new File(path));

        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST, url, requestParams,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo.result.equals("success")) {
                            ToastTool.showToast(getActivity(), "保存成功");
                        } else {
                            ToastTool.showToast(getActivity(), responseInfo.result);
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(getActivity(), "连接失败，请检查网络连接！！！");

                    }
                });

    }
}
