package com.leetai.purchasingagent.tools;

import android.os.Environment;

/**
 * Created by pc on 2016/2/23.
 */
public class Tools {
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
}