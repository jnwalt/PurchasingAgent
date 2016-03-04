package com.leetai.purchasingagent.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.leetai.purchasingagent.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EmailchangeActivity extends AppCompatActivity {

    @ViewInject(R.id.et_emailnew)
    EditText et_emailnew;
    String emailOld="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailchange);
        ViewUtils.inject(this);
        emailOld=getIntent().getStringExtra("email");
        et_emailnew.setText(emailOld);
        Editable etable = et_emailnew.getText();
        Selection.setSelection(etable, etable.length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emailchange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.emailsav) {

            String str_email=et_emailnew.getText().toString().trim();
            System.out.println("EmailchangeActivity"+str_email);

            Intent intent = new Intent();
            intent.putExtra("result", str_email);// 把返回数据存入Intent
            EmailchangeActivity.this.setResult(2, intent);// 设置回传数据。resultCode值是1，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
            EmailchangeActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
