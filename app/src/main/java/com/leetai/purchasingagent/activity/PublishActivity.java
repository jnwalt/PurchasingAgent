package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.GsonTool;
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
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishActivity extends Activity {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Publish publish;
    private String mParam1;
    private String mParam2;
    String type = "";

    //
    @ViewInject(R.id.et_title)
    EditText et_title;
    @ViewInject(R.id.et_description)
    EditText et_description;
    @ViewInject(R.id.et_address)
    EditText et_address;
    @ViewInject(R.id.et_price)
    EditText et_price;
    @ViewInject(R.id.btn_save)
    Button btn_save;
    @ViewInject(R.id.btn_publish)
    Button btn_publish;

    @OnClick(R.id.btn_save)
    public void saveClick(View v) {
        // System.out.println(et_title.getText().toString());
        if (checkEmpty()) {
            Publish publishAdd = getPublish(0);
            String str = GsonTool.classToJsonString(publishAdd);
            saveOrPublish(str);
            setResult(0);
            Thread thread = new Thread();
            try {
                thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    @OnClick(R.id.btn_publish)
    public void publishClick(View v) {
        // System.out.println(et_title.getText().toString());
        if (checkEmpty()) {
            Publish publishAdd = getPublish(1);
            String str = GsonTool.classToJsonString(publishAdd);
            saveOrPublish(str);
            setResult(0);
            Thread thread = new Thread();

            try {
                thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    }
//    public static PublishActivity newInstance(String param1, String param2) {
//        PublishActivity fragment = new PublishActivity();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public PublishActivity() {
//        // Required empty public constructor
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("modify")) {

            Bundle bundle = intent.getBundleExtra("bundle");
            publish = (Publish) bundle.getSerializable("publish");
            // System.out.println("publish.getTitle().toString()=" + publish.getTitle().toString());
            try {
                et_title.setText(publish.getTitle().toString());
                et_description.setText(publish.getDescription().toString());
                et_price.setText(publish.getPrice().toString());
                et_address.setText(publish.getAddress().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        // System.out.println("publisth.getID=" + publish.getId().toString());

    }


    private boolean checkEmpty() {
        if (TextUtils.isEmpty(et_title.getText().toString())) {
            ToastTool.showToast(this, "请输入标题");
            return false;
        }

        if (TextUtils.isEmpty(et_description.getText().toString())) {
            ToastTool.showToast(this, "请输入描述");
            return false;
        }

        if (TextUtils.isEmpty(et_address.getText().toString())) {
            ToastTool.showToast(this, "请输入地址");
            return false;
        }

        if (TextUtils.isEmpty(et_price.getText().toString())) {
            ToastTool.showToast(this, "请输入价格");
            return false;
        }
        return true;
    }

    private Publish getPublish(int i) {
        Publish publishNew = new Publish();
        try {
            String url = "";
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);

            publishNew.setTitle(et_title.getText().toString());
            publishNew.setDescription(et_description.getText().toString());
            publishNew.setPrice(Double.parseDouble(et_price.getText().toString()));
            publishNew.setAddress(et_address.getText().toString());

            if (type.equals("add")) {
                publishNew.setUserId((int) SharedPreferencesTool.get(this, "userId", 0));
                publishNew.setAddTime(date);
                publishNew.setPublicFlag(i);
                publishNew.setType("a");
            } else if (type.equals("modify")) {
                publishNew.setId(publish.getId());
                publishNew.setUserId((int) SharedPreferencesTool.get(this, "userId", 0));
                publishNew.setAddTime(publish.getAddTime());
                publishNew.setPublicFlag(publish.getPublicFlag());
                publishNew.setType("b");
            }


            return publishNew;

        } catch (NumberFormatException e) {
            ToastTool.showToast(this, "请输入数字");
            return publishNew;
        } catch (Exception e1) {
            e1.printStackTrace();
            return publishNew;
        }
    }

    private void saveOrPublish(String str) {

        String url = "";
        if (type.equals("add")) {
            url = HttpTool.getUrl(new String[]{"add", str}, "PublishServlet");
        } else if (type.equals("modify")) {
            url = HttpTool.getUrl(new String[]{"modify", str}, "PublishServlet");
        }


        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (!responseInfo.result.equals("fail")) {
                            ToastTool.showToast(PublishActivity.this, "保存成功");
                        } else {
                            ToastTool.showToast(PublishActivity.this, "保存失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(PublishActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });

    }


}
