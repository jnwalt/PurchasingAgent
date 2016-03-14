package com.leetai.purchasingagent.tools;

import android.content.Context;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
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
     * @return
     */
    public static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 取用户id
     * @return
     */
    public static int getUserId(Context context){

        return (int) SharedPreferencesTool.get(context,"userId",0);
    }
    /**
     * 取用户名称
     * @return
     */
    public static String getUserName(Context context){
        return (String) SharedPreferencesTool.get(context,"userName","无");
    }
    /**
     * 取用户头像本地路径
     * @return
     */
    public static String getUserHeadPath(int userId){

        return  Environment.getExternalStorageDirectory() + "/PurchasingAgent/User/" + userId + ".png";
    }
    /**
     * 取用户头像网络路径
     * @return
     */
    public static String getUserHeadURL(int userId){

        return    HttpTool.getPicUrl() + "/user/" + userId + ".png";
    }

    public static void initTitleView(Window view,String title) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.in_title);
        TextView tv_title = (TextView) linearLayout.findViewById(R.id.tv_title);
        tv_title.setText(title);
    }
}