package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.fragment.MyFragment;
import com.leetai.purchasingagent.fragment.PublishListFragment;
import com.leetai.purchasingagent.fragment.RecommendFragment;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


public class MainActivity extends Activity {
    private Fragment currentFragment;
    PublishListFragment publishListFragment;
    RecommendFragment recommendFragment;
    MyFragment myFragment;


    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @ViewInject(R.id.iv_recomend)
    ImageView btn_recomend;
    @ViewInject(R.id.iv_publish)
    ImageView btn_publish;
    @ViewInject(R.id.iv_my)
    ImageView btn_my;

    @ViewInject(R.id.tv_recomend)
    TextView tv_recomend;
    @ViewInject(R.id.tv_publish)
    TextView tv_publish;
    @ViewInject(R.id.tv_my)
    TextView tv_my;


    @OnClick(R.id.iv_recomend)
    public void recomendClick(View v) {
        if (recommendFragment == null) {
            recommendFragment = RecommendFragment.newInstance("", "");
        }

        addOrShowFragment(recommendFragment);
        btn_recomend.setImageResource(R.drawable.btn_recommend_pre);
        btn_publish.setImageResource(R.drawable.btn_publish_nor);
        btn_my.setImageResource(R.drawable.btn_my_nor);
        tv_recomend.setTextColor(getResources().getColor(R.color.bottomtab_press));
        tv_publish.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        tv_my.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    @OnClick(R.id.iv_publish)
    public void publishClick(View v) {
        if (publishListFragment == null) {
            publishListFragment = PublishListFragment.newInstance("", "");
        }
        addOrShowFragment(publishListFragment);
        btn_recomend.setImageResource(R.drawable.btn_recommend_nor);
        btn_publish.setImageResource(R.drawable.btn_publish_pre);
        btn_my.setImageResource(R.drawable.btn_my_nor);
        tv_recomend.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        tv_publish.setTextColor(getResources().getColor(R.color.bottomtab_press));
        tv_my.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    @OnClick(R.id.iv_my)
    public void myClick(View v) {

        if (myFragment == null) {
            myFragment = MyFragment.newInstance("", "");
        }
        addOrShowFragment(myFragment);


        btn_recomend.setImageResource(R.drawable.btn_recommend_nor);
        btn_publish.setImageResource(R.drawable.btn_publish_nor);
        btn_my.setImageResource(R.drawable.btn_my_pre);
        tv_recomend.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        tv_publish.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        tv_my.setTextColor(getResources().getColor(R.color.bottomtab_press));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        RecommendFragment recommendFragement = RecommendFragment.newInstance("", "");
        transaction
                .add(R.id.fl_content, recommendFragement);
        transaction.commit();
        currentFragment = recommendFragement;
        //   addOrShowFragment(transaction,recommendFragement);
    }


    private void addOrShowFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fl_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up btn_my, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastTool.showToast(MainActivity.this, "再按一次退出程序");
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
