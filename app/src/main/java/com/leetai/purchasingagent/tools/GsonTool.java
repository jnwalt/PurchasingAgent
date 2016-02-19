package com.leetai.purchasingagent.tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016-02-18.
 */
public class GsonTool {
    public static String classToJsonString(Object o){
        String str = "";
        Gson gson = new Gson();
        str = gson.toJson(o);
        return str;
    }


    public static <T> List<T> stringToList(String str,Class<T> cla ){
       List<T> list = new ArrayList<T>();
        Gson gson = new Gson();
        list = gson.fromJson(str, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }
}
