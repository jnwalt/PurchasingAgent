package com.leetai.purchasingagent.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.modle.Version;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;

import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {
    @ViewInject(R.id.tv_version)
    TextView tv_version;
    @ViewInject(R.id.tv_process)
    TextView tv_process;

    Version version = new Version();
    int localVersionCode;
    String localVersionName;
    List<Version> list;
    protected static final int EnterHome = 1;
    @ViewInject(R.id.rl_root)
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ViewUtils.inject(this);

        list = new ArrayList<Version>();
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_root);
        boolean autoUpdate = (boolean) SharedPreferencesTool.get(WelcomeActivity.this, "auto_update", true);

        if (autoUpdate) {
            getLocalVersion();
            getRemoteVersion();
        } else {
            handler.sendEmptyMessageDelayed(EnterHome, 3000);

        }
        //渐变效果
        AlphaAnimation anim = new AlphaAnimation(0.3f, 1f);
        anim.setDuration(2000);
        relativeLayout.startAnimation(anim);
    }

    private void checkVersion(List<Version> list) {
//        Log.i("Splash接收到的数据", list.get(0).getVersionCode());
//        Log.i("Splash接收到的数据", list.get(0).getVersionName());
//        Log.i("Splash接收到的数据", list.get(0).getDescription());
        if (Integer.parseInt(list.get(0).getVersionCode()) > localVersionCode) {
            showUpdateDialog(list);
        } else {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(EnterHome);
                }
            }.start();


        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EnterHome:

                    enterHome();
                    break;
            }

        }
    };

    private void showUpdateDialog(List<Version> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setTitle("最新版本" + list.get(0).getVersionName() + "，版本特性：" + list.get(0).getDescription());
        // builder.setCancelable(false);//点返回无效果  一般不用 用户体验太差
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoad();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    private void downLoad() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory() + "/update.apk");

            if (fileExist(file)) {
                file.delete();
            }
            tv_process.setVisibility(View.VISIBLE);

            String target = Environment.getExternalStorageDirectory() + "/update.apk";
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download("http://172.16.69.49:80/app-release.apk",
                    target,
                    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                    new RequestCallBack<File>() {
                        @Override
                        public void onStart() {
                            Log.i("WelcomeActivity", "正在开始下载……");
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            Log.i("WelcomeActivity", "下载进度：" + current + "/" + total);
                            tv_process.setText("下载进度:" + current * 100 / total + "%");
                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            Log.i("WelcomeActivity", "下载成功");
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                            //startActivity(intent);
                            startActivityForResult(intent, 0);
                        }


                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(WelcomeActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                            System.out.println(error + msg);
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    handler.sendEmptyMessage(EnterHome);
                                }
                            }.start();
                        }


                    });
        } else {
            Toast.makeText(WelcomeActivity.this, "没找到sd卡", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean fileExist(File file) {
        try {
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private void getRemoteVersion() {


        String url = HttpTool.getUrl("", "VersionServlet");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // Log.i("Splash接收到的数据", str);
                        Gson gson = new Gson();
                        //  version = gson.fromJson(str, Version.class);
                        Version version = new Version();
                        list = new ArrayList<Version>();
                        System.out.println("responseInfo.result=" + responseInfo.result);
                        version = gson.fromJson(responseInfo.result, Version.class);
                        list.add(version);
                        checkVersion(list);
                    }

                    @Override
                    public void onStart() {
                        System.out.println("onStart");
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        Toast.makeText(WelcomeActivity.this, "连接失败，请检查网络连接！！！", Toast.LENGTH_SHORT).show();

                        handler.sendEmptyMessage(EnterHome);
                        System.out.println("onFailure=" + msg);
                    }
                });

    }

    private void getLocalVersion() {
        PackageManager packageManager = getPackageManager();
        int versionCode;
        String versionName = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            localVersionCode = packageInfo.versionCode;
            localVersionName = packageInfo.versionName;
            tv_version.setText("版本号：" + localVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    private void enterHome() {
        Intent intent;
        Boolean b = (Boolean) SharedPreferencesTool.get(this, "isLogin", false);
        if (b) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
