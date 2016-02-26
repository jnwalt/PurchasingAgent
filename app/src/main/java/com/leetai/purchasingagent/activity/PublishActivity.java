package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.BitMapTool;
import com.leetai.purchasingagent.tools.GsonTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ImageTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishActivity extends Activity {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int img_num = 0;
    Publish publish;
    private String mParam1;
    private String mParam2;
    String type = "";
List<String> listString = new ArrayList<String>();
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
    @ViewInject(R.id.im_pic1)
    ImageView im_pic1;

    @ViewInject(R.id.im_pic2)
    ImageView im_pic2;

    @ViewInject(R.id.im_pic3)
    ImageView im_pic3;


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

    @OnClick(R.id.im_pic1)
    public void pick1Click(View v) {
        ImageTool.showImagePickDialog(PublishActivity.this);
        img_num = 1;
    }

    @OnClick(R.id.im_pic2)
    public void pick2Click(View v) {
        ImageTool.showImagePickDialog(PublishActivity.this);
        img_num = 2;
    }

    @OnClick(R.id.im_pic3)
    public void pick3Click(View v) {
        ImageTool.showImagePickDialog(PublishActivity.this);
        img_num = 3;
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
//        if (type.equals("add")) {
//
//        } else if (type.equals("modify")) {
//            url = HttpTool.getUrl(new String[]{"modify", str}, "PublishServlet");
//        }
        url = HttpTool.getUrl("", "PublishServlet");
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("param1","add");
        requestParams.addBodyParameter("param2",str);
        for(int i=0;i<listString.size();i++) {
            requestParams.addBodyParameter(i+"", new File(listString.get(i)));
        }

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, requestParams,
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageTool.REQUEST_CODE_FROM_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    String path = ImageTool.getImageAbsolutePath(PublishActivity.this, uri);
                    listString.add(path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    bitmap = BitMapTool.cutToCrop(bitmap);
                    switch (img_num) {
                        case 0:
                            break;
                        case 1:
                            im_pic1.setImageBitmap(bitmap);
                            break;
                        case 2:
                            im_pic2.setImageBitmap(bitmap);
                            break;
                        case 3:
                            im_pic3.setImageBitmap(bitmap);
                            break;
                        default:
                            break;

                    }
                }
                break;
            case ImageTool.REQUEST_CODE_FROM_CAMERA:
                //data.getData();  这样获取的bitmap是压缩图
                if (resultCode == RESULT_CANCELED) {
                    ImageTool.deleteImageUri(this, ImageTool.imageUriFromCamera);
                } else {
                    Uri imageUriCamera = ImageTool.imageUriFromCamera;
                    Log.i("imageUriCamera=", imageUriCamera.toString());
                    String path = ImageTool.getImageAbsolutePath(PublishActivity.this, imageUriCamera);
                    Log.i("path=", path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    switch (img_num) {
                        case 0:
                            break;
                        case 1:
                            im_pic1.setImageBitmap(bitmap);
                            break;
                        case 2:
                            im_pic2.setImageURI(imageUriCamera);
                            break;
                        case 3:
                            im_pic3.setImageBitmap(bitmap);
                            break;
                        default:
                            break;

                    }
                }


                break;
            default:
                break;
        }
    }
}
