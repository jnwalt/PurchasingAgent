package com.leetai.purchasingagent.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TelchangeActivity extends AppCompatActivity {

    @ViewInject(R.id.et_telnew)
    EditText et_telnew;
    String telOld="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telchange);
        ViewUtils.inject(this);
        telOld=getIntent().getStringExtra("tel");
        et_telnew.setText(telOld);
        Editable etable = et_telnew.getText();
        Selection.setSelection(etable, etable.length());
       // ActionBar actionBar = getActionBar();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_telchange, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.telsav) {

            String str_tel=et_telnew.getText().toString().trim();
            System.out.println("TelchangeActivity"+str_tel);

            Intent intent = new Intent();
            intent.putExtra("result", str_tel);// 把返回数据存入Intent
            TelchangeActivity.this.setResult(1, intent);// 设置回传数据。resultCode值是1，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
            TelchangeActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
