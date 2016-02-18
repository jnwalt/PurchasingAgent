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

public class LoginActivity extends Activity {
    @ViewInject(R.id.et_username)
    EditText et_username;
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.btn_login)
    Button btn_login;
    @ViewInject(R.id.btn_regist)
    Button btn_regist;

    @OnClick(R.id.btn_login)
    public void loginClick(View v) {

        String url = HttpTool.getUrl("", "LoginServlet");
        String username, password;
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        if (!TextUtils.isEmpty(username)){
            if (!TextUtils.isEmpty(password)){
                RequestParams params = HttpTool.getParam(new String[]{username, password});

                HttpUtils http = new HttpUtils();

                http.send(HttpRequest.HttpMethod.POST, url, params,
                        new RequestCallBack<String>() {
                            @Override
                            public void onLoading(long total, long current, boolean isUploading) {
                                //  System.out.println("正在连接");
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                LoginActivity.this.onSuccess(responseInfo.result);
                                //   System.out.println("responseInfo=" + responseInfo.result);
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
            }else {
                Toast.makeText(this,"请输入密码！",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"请输入用户名！",Toast.LENGTH_SHORT).show();
        }



    }

    @OnClick(R.id.btn_regist)
    public void registClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);

    }

    public void onSuccess(String result) {
        if (result.equals("登陆成功")) {
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferencesTool.put(LoginActivity.this, "isLogin", true);
            startActivity(intent);

        } else {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }

}
