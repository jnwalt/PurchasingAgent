package com.leetai.purchasingagent.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leetai.purchasingagent.R;

/**
 * Created by pc on 2016/2/23.
 */
public class Tools {


    public final static String ACTION_TYPE_QUERY_DETAIL = "queryDetail";
    public final static String ACTION_TYPE_QUERY = "query";
    public final static String ACTION_TYPE_ADD = "add";
    public final static String ACTION_TYPE_MODIFY = "modify";
    public final static String ACTION_TYPE_DELETE = "delete";

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取用户id
     *
     * @return
     */
    public static int getUserId(Context context) {

        return (int) SharedPreferencesTool.get(context, "userId", 0);
    }

    /**
     * 取用户名称
     *
     * @return
     */
    public static String getUserName(Context context) {
        return (String) SharedPreferencesTool.get(context, "userName", "无");
    }

    /**
     * 取用户头像本地路径
     *
     * @return
     */
    public static String getUserHeadPath(int userId) {

        return Environment.getExternalStorageDirectory() + "/PurchasingAgent/User/" + userId + ".png";
    }

    /**
     * 取用户头像网络路径
     *
     * @return
     */
    public static String getUserHeadURL(int userId) {

        return HttpTool.getPicUrl() + "/user/" + userId + ".png";
    }
    /**
     * 使用activity参数1.activity 2.getWindow() 3.标题名称  4.右侧名称  5.监听事件 6.是否显示返回箭头
     *
     * @return
     */
    public static void initTitleView(final Activity activity, Window view, int title, int  right, View.OnClickListener listener,boolean b) {

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.in_title);
        TextView tv_title = (TextView) relativeLayout.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) relativeLayout.findViewById(R.id.tv_right);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        tv_title.setText(title);
        tv_right.setText(right);
        if (listener == null) {
            tv_right.setVisibility(View.INVISIBLE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setOnClickListener(listener);
        }

        if (b) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 使用activity参数1.activity 2.getWindow() 3.标题名称  4.右侧名称  5.监听事件 6.是否显示返回箭头
     *
     * @return
     */
    public static void initTitleView(final Activity activity, Window view, String title, String  right, View.OnClickListener listener,boolean b) {

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.in_title);
        TextView tv_title = (TextView) relativeLayout.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) relativeLayout.findViewById(R.id.tv_right);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        tv_title.setText(title);
        tv_right.setText(right);
        if (listener == null) {
            tv_right.setVisibility(View.INVISIBLE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setOnClickListener(listener);
        }

        if (b) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 适用fragment参数1.View 2. 标题名称  3.右侧名称  5.监听事件
     * fragment没有返回箭头
     * @return
     */
    public static void initTitleView(View view, int i,int ii, View.OnClickListener listener) {

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.in_title);
        TextView tv_title = (TextView) relativeLayout.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) relativeLayout.findViewById(R.id.tv_right);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.iv_back);
        imageView.setVisibility(View.INVISIBLE);
        tv_title.setText(i);
        tv_right.setText(ii);
        if (listener == null) {
            tv_right.setVisibility(View.INVISIBLE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setOnClickListener(listener);
        }

    }
}