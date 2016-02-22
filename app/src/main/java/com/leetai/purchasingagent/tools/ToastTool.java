package com.leetai.purchasingagent.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dell on 2016-02-18.
 */
public class ToastTool {
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
