package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.User;
import com.leetai.purchasingagent.tools.BitMapTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.ImageTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.leetai.purchasingagent.tools.Tools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserinfoActivity extends Activity {

    List<User> list;
    Map<String, String> map;
    List<Map<String, String>> listmap;
    SimpleAdapter adapter;
    ListView lv;
    TextView tv;
    Button btn;
    String uname = "";
    String return_result = "";
    User user = new User();
    @ViewInject(R.id.tv_username)
    TextView tv_username;
    @ViewInject(R.id.tv_password)
    TextView tv_password;
    @ViewInject(R.id.tv_telphone)
    TextView tv_telphone;
    @ViewInject(R.id.tv_email)
    TextView tv_email;
    @ViewInject(R.id.iv_head)
    ImageView iv_head;
    int userId;
    private String[] items = new String[]{"选择本地图片", "拍照"};
    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    /* 请求码*/

    private static final int IMAGE_REQUEST_CODE = 10;
    private static final int CAMERA_REQUEST_CODE = 11;
    private static final int RESULT_REQUEST_CODE = 12;


    @OnClick(R.id.tv_telphone)
    public void telphoneClick(View v) {

        Intent intent = new Intent(this, TelchangeActivity.class);
        String tel = tv_telphone.getText().toString().trim();
        intent.putExtra("tel", tel);
        startActivityForResult(intent, 1);

    }

    @OnClick(R.id.tv_email)
    public void emailClick(View v) {

        Intent intent = new Intent(this, EmailchangeActivity.class);
        String email = tv_email.getText().toString().trim();
        intent.putExtra("email", email);
        startActivityForResult(intent, 2);

    }

    @OnClick(R.id.tv_password)
    public void passwordClick(View v) {

        Intent intent = new Intent(this, PasschangeActivity.class);
        String password = tv_password.getText().toString().trim();
        intent.putExtra("password", password);
        startActivityForResult(intent, 3);

    }

    @OnClick(R.id.iv_head)
    public void headClick(View v) {
        ImageTool.showImagePickDialog(UserinfoActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ViewUtils.inject(this);
        init();


        SharedPreferences sp = this.getSharedPreferences("user", Context.MODE_PRIVATE);     //获得对象
        uname = sp.getString                                 //从中读取用户名
                ("username",                                      //键值
                        null);
        if (uname == null) {
            //Toast.makeText(UserinfoActivity.this,"没有登录",Toast.LENGTH_SHORT).show();
            uname = "123";
        }
        getUserInfo();


        //默认值
//            if(uname==null){                                           //用户名若为空
//                  Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
//                gotoLoginView();                                       //返回到登录界面
//            }else {
//                   SharedPreferences.Editor editor=sp.edit();             //获取Editor的引用
//                  editor.putString("username",uname);                       //放入用户名
//                editor.commit();}                                          //提交
    }

    private void init() {
        userId = Tools.getUserId(UserinfoActivity.this);
        BitmapUtils bitmapUtils = BitMapTool.getBitmapUtils(UserinfoActivity.this);
        String path = Tools.getUserHeadPath(userId);
        bitmapUtils.clearCache(path);
        bitmapUtils.display(iv_head, path, new CustomBitmapLoadCallBack());
    }

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            bitmap = BitMapTool.cutToRound(bitmap);
            container.setImageBitmap(bitmap);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            super.onLoadFailed(container, uri, drawable);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaulthad);
            bitmap = BitMapTool.cutToRound(bitmap);
            container.setImageBitmap(bitmap);
        }
    }

    public void gotoLoginView() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 0);

    }

//
//    /**
//     * 显示选择对话框
//     */
//    private void showDialog() {
//
//        new AlertDialog.Builder(this)
//                .setTitle("设置头像")
//                .setItems(items, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        switch (which) {
//                            case 0:
//                                choseHeadImageFromGallery();
//                                break;
//                            case 1:
//
//                                choseHeadImageFromCameraCapture();
//                                break;
//                        }
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
//
//    }

//    // 从本地相册选取图片作为头像
//    private void choseHeadImageFromGallery() {
//        Intent intentFromGallery = new Intent();
//        // 设置文件类型
//        intentFromGallery.setType("image/*");
//        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
//    }
//
//    // 启动手机相机拍摄照片作为头像
//    private void choseHeadImageFromCameraCapture() {
//        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        // 判断存储卡是否可用，存储照片文件
//        if (Tools.hasSdcard()) {
//            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                    .fromFile(new File(Environment
//                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
//        }
//
//        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
//    }

    public void getUserInfo() {

        list = new ArrayList<User>();
        String url = HttpTool.getUrl("", "UserinfoServlet");
        RequestParams params = HttpTool.getParam(new String[]{uname, "doQuery"});
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        // System.out.println("正在连接");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("responseInfo=" + responseInfo.result);
                        UserinfoActivity.this.onSuccess(responseInfo.result);

                    }

                    @Override
                    public void onStart() {
                        System.out.println("onStart");
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        System.out.println("onFailure=" + msg);
                        Toast.makeText(UserinfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (resultCode) {
                case 1:         // 子窗口telchangeActivity的回传数据
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            //处理代码在此地
                            return_result = bundle.getString("result");// 得到子窗口ChildActivity的回传数据
                            user.setTelphone(return_result);
                            Gson gson = new Gson();
                            String param = gson.toJson(user);
                            String url = HttpTool.getUrl("", "UserinfoServlet");
                            RequestParams params = HttpTool.getParam(new String[]{uname, "doUpdate", param});
                            HttpUtils http = new HttpUtils();
                            http.send(HttpRequest.HttpMethod.POST, url, params,
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onLoading(long total, long current, boolean isUploading) {
                                            // System.out.println("正在连接");
                                        }

                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            if (responseInfo.result.equals("1")) {
                                                tv_telphone.setText(return_result);
                                                Toast.makeText(UserinfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                                //getUserInfo();
                                            } else {
                                                Toast.makeText(UserinfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                                            }
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


                        }
                    }
                    break;
                case 2:         // 子窗口emailchangeActivity的回传数据
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            //处理代码在此地
                            return_result = bundle.getString("result");// 得到子窗口ChildActivity的回传数据
                            user.setEmail(return_result);
                            Gson gson = new Gson();
                            String param = gson.toJson(user);
                            String url = HttpTool.getUrl("", "UserinfoServlet");
                            RequestParams params = HttpTool.getParam(new String[]{uname, "doUpdate", param});
                            HttpUtils http = new HttpUtils();
                            http.send(HttpRequest.HttpMethod.POST, url, params,
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onLoading(long total, long current, boolean isUploading) {
                                            // System.out.println("正在连接");
                                        }

                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            if (responseInfo.result.equals("1")) {
                                                tv_email.setText(return_result);
                                                Toast.makeText(UserinfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                                //getUserInfo();
                                            } else {
                                                Toast.makeText(UserinfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                                            }
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


                        }
                    }
                    break;
                case 3:         // 子窗口passchangeActivity的回传数据
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            //处理代码在此地
                            return_result = bundle.getString("result");// 得到子窗口passchangeActivity的回传数据
                            user.setPassword(return_result);
                            Gson gson = new Gson();
                            String param = gson.toJson(user);
                            String url = HttpTool.getUrl("", "UserinfoServlet");
                            RequestParams params = HttpTool.getParam(new String[]{uname, "doUpdate", param});
                            HttpUtils http = new HttpUtils();
                            http.send(HttpRequest.HttpMethod.POST, url, params,
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onLoading(long total, long current, boolean isUploading) {
                                            // System.out.println("正在连接");
                                        }

                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            if (responseInfo.result.equals("1")) {
                                                tv_password.setText(return_result);
                                                Toast.makeText(UserinfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                                //getUserInfo();
                                            } else {
                                                Toast.makeText(UserinfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                                            }
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


                        }
                    }
                    break;
//                case IMAGE_REQUEST_CODE:
//                    startPhotoZoom(data.getData());
//                    break;
//                case CAMERA_REQUEST_CODE:
//                    if (Tools.hasSdcard()) {
//                        File tempFile = new File(
//                                Environment.getExternalStorageDirectory()
//                                        + IMAGE_FILE_NAME);
//                        startPhotoZoom(Uri.fromFile(tempFile));
//                    } else {
//                        Toast.makeText(UserinfoActivity.this, "未找到存储卡，无法存储照片！",
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    break;
//                case RESULT_REQUEST_CODE:
//                    if (data != null) {
//                        getImageToView(data);
//                    }
//                    break;


            }
        }
        switch (requestCode) {
            case ImageTool.REQUEST_CODE_FROM_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    String path = ImageTool.getImageAbsolutePath(UserinfoActivity.this, uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    bitmap = BitMapTool.getSmallBitmap(path);
                    bitmap = BitMapTool.cutToCrop(bitmap);
                    String newPath = BitMapTool.saveBitmapToLocal(bitmap, Tools.getUserId(UserinfoActivity.this) + "");
                    iv_head.setImageBitmap(bitmap);

                    saveHeadOnLine(newPath);
                }
                break;
            case ImageTool.REQUEST_CODE_FROM_CAMERA:

                break;
            default:

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    /**
//     * 裁剪图片方法实现
//     *
//     * @param uri
//     */
//    public void startPhotoZoom(Uri uri) {
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 320);
//        intent.putExtra("outputY", 320);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, 2);
//    }
//
//    /**
//     * 保存裁剪之后的图片数据
//     *
//     * @param
//     */
//    private void getImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            Drawable drawable = new BitmapDrawable(photo);
//            iv_head.setImageDrawable(drawable);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_userinfo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSuccess(String result) {


        Gson gson = new Gson();

        list = new ArrayList<User>();
        user = gson.fromJson(result, User.class);
        if (user != null) {
            tv_username.setText(user.getUsername());
            tv_password.setText(user.getPassword());
            tv_telphone.setText(user.getTelphone());
            tv_email.setText(user.getEmail());
        }

    }

    private void saveHeadOnLine(String path) {
        String url = "";
        url = HttpTool.getUrl("", "UserHeadServlet");
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("param1", "add");
        requestParams.addBodyParameter("head", new File(path));

        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST, url, requestParams,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo.result.equals("success")) {
                            ToastTool.showToast(UserinfoActivity.this, "保存成功");
                        } else {
                            ToastTool.showToast(UserinfoActivity.this, responseInfo.result);
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(UserinfoActivity.this, "连接失败，请检查网络连接！！！");

                    }
                });

    }
}
