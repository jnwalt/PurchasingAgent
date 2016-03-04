package com.leetai.purchasingagent.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leetai.purchasingagent.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnFocusChange;

import java.security.spec.PSSParameterSpec;

public class PasschangeActivity extends AppCompatActivity {
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.et_comfirmpassword)
    EditText et_comfirmpassword;
    @ViewInject(R.id.tv_passwordmsg)
    TextView tv_passwordmsg;
    @ViewInject(R.id.cb_show)
    CheckBox cb_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passchange);
        ViewUtils.inject(this);

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str_password = et_password.getText().toString().trim();
                String str_comfirmpassword = et_comfirmpassword.getText().toString().trim();
                if (str_comfirmpassword.isEmpty()) {
                    return;
                } else {
                    if (!str_password.equals(str_comfirmpassword)) {
                        tv_passwordmsg.setText("两次密码不一致，请确认");
                        et_password.setFocusable(true);
                        return;
                    } else {
                        tv_passwordmsg.setText("");
                        return;
                    }
                }

            }
        });
        et_comfirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str_password=et_password.getText().toString().trim();
                String str_comfirmpassword=et_comfirmpassword.getText().toString().trim();
                if(str_password.isEmpty())
                {
                    tv_passwordmsg.setText("请确认两次密码都不为空");
                    return;
                }else
                {
                    if(str_comfirmpassword.length()>=str_password.length()) {
                        if (!str_password.equals(str_comfirmpassword)) {
                            tv_passwordmsg.setText("两次密码不一致，请确认");
                            et_comfirmpassword.setFocusable(true);
                            return;
                        } else {
                            tv_passwordmsg.setText("");
                            return;
                        }
                    }
                }

            }
        });
        cb_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if(cb_show.isChecked()){
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_comfirmpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else
                {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_comfirmpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_passchange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.passsave) {

            String str_password=et_password.getText().toString().trim();
            String str_comfirmpassword=et_comfirmpassword.getText().toString().trim();
            if(str_password.isEmpty()|| str_comfirmpassword.isEmpty() )
            {
                Toast.makeText(PasschangeActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                return false;
            }
            if(!str_password.equals(str_comfirmpassword))
            {

                Toast.makeText(PasschangeActivity.this,"密码不一致，请确认！",Toast.LENGTH_SHORT).show();
                return false;
            }
            System.out.println("PasschangeActivity"+str_password);

            Intent intent = new Intent();
            intent.putExtra("result", str_password);// 把返回数据存入Intent
            PasschangeActivity.this.setResult(3, intent);// 设置回传数据。resultCode值是1，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
            PasschangeActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
