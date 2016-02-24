package com.leetai.purchasingagent.test;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestActivity extends Activity {
    private final String TAG = "error";
    private final String IMAGE_TYPE = "image/*";

    private final int GALLARY_REQUEST_CODE = 2;
    private static int CAMERA_REQUEST_CODE = 1;
    @ViewInject(R.id.btn_showimg)
    Button btn_showimg;

    @ViewInject(R.id.btn_showcamera)
    Button btn_showcamera;

    @ViewInject(R.id.im)
    ImageView im;

    @ViewInject(R.id.tv_test)
    TextView tv_test;


    @OnClick(R.id.btn_showimg)
    public void showClick(View view) {
        //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, GALLARY_REQUEST_CODE);
    }

    @OnClick(R.id.btn_showcamera)
    public void btn_showcameraClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ViewUtils.inject(this);


    }
//重写onActivityResult以获得你需要的信息


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data == null) {
                return;
            } else {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
                    im.setImageBitmap(bitmap);
                }
            }
        } else if (requestCode == GALLARY_REQUEST_CODE) {
            if (data == null) {
                return;
            } else {
                Uri uri = data.getData();
                Log.i("uri:", uri.toString());
                try {

                    Bitmap bitmap = getPicFromBytes(null, uri);
                    im.setImageBitmap(bitmap);
                    Uri uri1 = saveBitmap(bitmap);
                   // Log.i("uri1:", uri1.toString());
//                    Bitmap bitmap =   MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                    im.setImageBitmap(bitmap);

//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                    byte[] bytes = baos.toByteArray();


                    String path = "/sdcard/pa/test.png";
                    String url = HttpTool.getUrl("", "UpLoadServlet");
//                     InputStream  InputStream = new FileInputStream(path);
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("param", new File(path));
                    HttpUtils http = new HttpUtils();

                    http.send(HttpRequest.HttpMethod.POST, url, params,
                            new RequestCallBack<String>() {
                                @Override
                                public void onStart() {
                                    tv_test.setText("conn...");
                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                    if (isUploading) {
                                        tv_test.setText("upload: " + current + "/" + total);
                                    } else {
                                        tv_test.setText("reply: " + current + "/" + total);
                                    }
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    tv_test.setText("reply: " + responseInfo.result);
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    tv_test.setText(error.getExceptionCode() + ":" + msg);
                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public Bitmap getPicFromBytes(
            BitmapFactory.Options opts, Uri uri) {

        ContentResolver resolver = getContentResolver();
        try {
            byte[] bytes = readStream(resolver.openInputStream(uri));

            if (bytes != null)
                if (opts != null)
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                            opts);
                else
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

    public Uri saveBitmap(Bitmap bitmap) {
        Log.e(TAG, "保存图片");

        File f = new File("/sdcard/pa");
        if (!f.exists()) {
            f.mkdir();
        }
        File png = new File("/sdcard/pa", "test.png");
        if (png.exists()) {
            png.delete();
        }
        if (!png.exists()) {
            try {
                png.createNewFile();
                FileOutputStream out = new FileOutputStream(png);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();

                Log.i(TAG, "已经保存");
                return Uri.fromFile(png);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }

        }
        return Uri.fromFile(png);
    }
}