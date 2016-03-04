package com.leetai.purchasingagent.tools;

import android.widget.ImageView;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.leetai.purchasingagent.R;

/**
 * Created by pc on 2016/2/25.
 */
public class MyImgBtn  extends LinearLayout{

    private ImageView mImgView = null;
    private TextView mTextView = null;
    private Context mContext;


    public MyImgBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        View inflate = LayoutInflater.from(context).inflate(R.layout.myimgbtn_layout, this, true);
        mContext = context;
        mImgView = (ImageView)inflate.findViewById(R.id.img);
        mTextView = (TextView)inflate.findViewById(R.id.text);


    }



    /*设置图片接口*/
    public void setImageResource(int resId){
        mImgView.setImageResource(resId);
    }

    /*设置文字接口*/
    public void setText(String str){
        mTextView.setText(str);
    }
    /*设置文字大小*/
    public void setTextSize(float size){
        mTextView.setTextSize(size);
    }




//     /*设置触摸接口*/
//    public void setOnTouch(OnTouchListener listen){
//        mImgView.setOnTouchListener(listen);
//        //mTextView.setOnTouchListener(listen);
//    }

}
