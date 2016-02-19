package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RegistActivity extends Activity {
    @ViewInject(R.id.et_username)
    EditText et_username;
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.et_comfirmpassword)
    EditText et_comfirmpassword;
    @ViewInject(R.id.btn_regist)
    Button btn_regist;

    @OnClick(R.id.btn_regist)
    public void registClick(View v) {
        String username, password, comfirmPassword;
        if (!TextUtils.isEmpty(et_username.getText().toString())) {
            if (!TextUtils.isEmpty(et_password.getText().toString()))
                if (!TextUtils.isEmpty(et_comfirmpassword.getText().toString())) {
                    username = et_username.getText().toString();
                    password = et_password.getText().toString();
                    comfirmPassword = et_comfirmpassword.getText().toString();
                    if (password.equals(comfirmPassword)) {
                        String url = HttpTool.getUrl("", "RegistServlet");
                        RequestParams params = HttpTool.getParam(new String[]{username, password});
                        HttpUtils http = new HttpUtils();

                        http.send(HttpRequest.HttpMethod.POST, url, params,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onLoading(long total, long current, boolean isUploading) {
                                        // System.out.println("正在连接");
                                    }

                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        RegistActivity.this.onSuccess(responseInfo.result.toString());
                                    }


                                    @Override
                                    public void onStart() {
                                        System.out.println("onStart");
                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {

                                        System.out.println("onFailure=" + msg);
                                    }
                                });
                    } else {
                        Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "请输入验证密码！", Toast.LENGTH_SHORT).show();
                }
            else {
                Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入用户名！", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ViewUtils.inject(this);
    }

    public void onSuccess(String result) {
        String msg = "";
        if (result.equals("1")) {
            msg = "注册成功";
        } else {
            msg = result;

        }
        try {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistActivity.this, MainActivity.class));
            SharedPreferencesTool.put(RegistActivity.this, "isLogin", true);
            SharedPreferencesTool.put(RegistActivity.this, "username", et_username.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
