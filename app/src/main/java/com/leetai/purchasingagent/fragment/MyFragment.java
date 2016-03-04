package com.leetai.purchasingagent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.OrderListActivity;
import com.leetai.purchasingagent.activity.SettingActivity;
import com.leetai.purchasingagent.activity.UserinfoActivity;
import com.leetai.purchasingagent.tools.MyImgBtn;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyImgBtn MyIBtn1 = null;
    private MyImgBtn MyIBtn2 = null;
    private MyImgBtn MyIBtn3 = null;
    TextView tv_all_order;
    TextView tv_accout;

    TextView tv_setting;
    private String mParam1;
    private String mParam2;


    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        tv_accout = (TextView) view.findViewById(R.id.tv_accout);
        tv_setting = (TextView) view.findViewById(R.id.tv_setting);
        tv_all_order= (TextView) view.findViewById(R.id.tv_all_order);
        MyIBtn1 = (MyImgBtn) view.findViewById(R.id.MyImgBtn1);
        MyIBtn1.setImageResource(R.drawable.launcher_bg);
        MyIBtn1.setText("待付款");
        MyIBtn1.setTextSize(13.0f);
        MyIBtn2 = (MyImgBtn) view.findViewById(R.id.MyImgBtn2);
        MyIBtn2.setImageResource(R.drawable.launcher_bg);
        MyIBtn2.setText("待收货");
        MyIBtn2.setTextSize(13.0f);
        MyIBtn3 = (MyImgBtn) view.findViewById(R.id.MyImgBtn3);
        MyIBtn3.setImageResource(R.drawable.launcher_bg);
        MyIBtn3.setText("待评价");
        MyIBtn3.setTextSize(13.0f);
        tv_accout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserinfoActivity.class);
                startActivity(intent);
            }
        });
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        tv_all_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });
    }


}
