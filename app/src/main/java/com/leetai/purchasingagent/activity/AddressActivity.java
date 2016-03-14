package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Address;
import com.leetai.purchasingagent.tools.BaiduMapTool;
import com.leetai.purchasingagent.tools.GsonTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.leetai.purchasingagent.tools.Tools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnTouch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddressActivity extends Activity {

    Address address = new Address();
    private int REQUEST_CODE;
    String type;
    @ViewInject(R.id.et_name)
    EditText et_name;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_region)
    EditText et_region;
    @ViewInject(R.id.et_code)
    EditText et_code;
    @ViewInject(R.id.et_detail)
    EditText et_detail;
    @ViewInject(R.id.cb_default)
    CheckBox cb_default;
    @ViewInject(R.id.btn_save)
    Button btn_save;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListerner();

    public class MyLocationListerner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String add = location.getCountry()+location.getProvince()+location.getCity()+location.getDistrict();
            if (!TextUtils.isEmpty(location.getCity())){
                et_region.setText(add);
            }else {
            Intent intent = new Intent(AddressActivity.this, RegionProvinceActivity.class);
            startActivity(intent);}
        }
    }

    @OnTouch(R.id.et_region)
    public boolean regionClick(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
            mLocationClient.registerLocationListener(myListener);    //注册监听函数
            mLocationClient.setLocOption(BaiduMapTool.getOption());
            mLocationClient.start();

        }
        return true;
    }

//    @OnClick(R.id.btn_save)
//    public void saveClick(View view) {
//        if (checkEmpty()) {
//            Address addressAdd = getAddress();
//            String str = GsonTool.classToJsonString(addressAdd);
//            save(str);
//            String defaultAddress = addressAdd.getRegion()+"   "+addressAdd.getDetail()+"   "+addressAdd.getName()+"   "+addressAdd.getPhone();
//            SharedPreferencesTool.put(AddressActivity.this, "defaultAddress", defaultAddress);
//            Intent intent = new Intent();
//            intent.putExtra("defaultAddress",defaultAddress);
//            setResult(REQUEST_CODE,intent);
//            Thread thread = new Thread();
//            try {
//                thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            finish();
//        }
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        REQUEST_CODE = intent.getIntExtra("req_code", 0);
        type = intent.getStringExtra("type");
        Tools.initTitleView(AddressActivity.this, getWindow(), R.string.title_activity_address, R.string.save, listener, true);

        if (type.equals("modify")) {

            Bundle bundle = intent.getBundleExtra("bundle");
            address = (Address) bundle.getSerializable("address");
            // System.out.println("publish.getTitle().toString()=" + publish.getTitle().toString());
            try {
                et_name.setText(address.getName().toString());
                et_phone.setText(address.getPhone().toString());
                et_region.setText(address.getRegion().toString());
                et_detail.setText(address.getDetail().toString());
                et_code.setText(address.getCode().toString());
                if (address.getDefaultadd() == 1) {
                    cb_default.setChecked(true);
                } else {
                    cb_default.setChecked(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkEmpty()) {
                Address addressAdd = getAddress();
                String str = GsonTool.classToJsonString(addressAdd);
                save(str);
                String defaultAddress = addressAdd.getRegion() + "   " + addressAdd.getDetail() + "   " + addressAdd.getName() + "   " + addressAdd.getPhone();
                SharedPreferencesTool.put(AddressActivity.this, "defaultAddress", defaultAddress);
                Intent intent = new Intent();
                intent.putExtra("defaultAddress", defaultAddress);
                setResult(REQUEST_CODE, intent);
                Thread thread = new Thread();
                try {
                    thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }

        }
    };

    private boolean checkEmpty() {
        if (TextUtils.isEmpty(et_name.getText().toString())) {
            ToastTool.showToast(this, "请输入收件人");
            return false;
        }

        if (TextUtils.isEmpty(et_phone.getText().toString())) {
            ToastTool.showToast(this, "请输入联系电话");
            return false;
        }

        if (TextUtils.isEmpty(et_region.getText().toString())) {
            ToastTool.showToast(this, "请输入所在地区");
            return false;
        }

//        if (TextUtils.isEmpty(et_code.getText().toString())) {
//            ToastTool.showToast(this, "请输入邮编");
//            return false;
//        }
        if (TextUtils.isEmpty(et_detail.getText().toString())) {
            ToastTool.showToast(this, "请输入详细地址");
            return false;
        }
        return true;
    }

    private Address getAddress() {
        Address addressNew = new Address();
        int i = 0;

        addressNew.setName(et_name.getText().toString());
        addressNew.setPhone(et_phone.getText().toString());
        addressNew.setRegion(et_region.getText().toString());
        addressNew.setCode(et_code.getText().toString());
        addressNew.setDetail(et_detail.getText().toString());
        addressNew.setUserId((int) SharedPreferencesTool.get(this, "userId", 0));

        if (cb_default.isChecked()) {
            i = 1;
        } else {
            i = 0;
        }
        addressNew.setDefaultadd(i);


        if (type.equals("add")) {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            addressNew.setAddtime(date);
        } else if (type.equals("modify")) {
            addressNew.setAddtime(address.getAddtime());
            addressNew.setId(address.getId());
        }
        return addressNew;
    }

    private void save(String str) {
        String url = "";
        if (type.equals("add")) {
            url = HttpTool.getUrl(new String[]{"add", str}, "AddressServlet");
        } else if (type.equals("modify")) {
            url = HttpTool.getUrl(new String[]{"modify", str}, "AddressServlet");
        }


        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (!responseInfo.result.equals("fail")) {
                            ToastTool.showToast(AddressActivity.this, "保存成功");
                        } else {
                            ToastTool.showToast(AddressActivity.this, "保存失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(AddressActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String region = SharedPreferencesTool.get(AddressActivity.this, "region", "未找到").toString();
        // Log.i("onRestart", region);
        et_region.setText(region);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }
}
