package com.leetai.purchasingagent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leetai.purchasingagent.R;
import com.leetai.purchasingagent.activity.BidedListActivity;
import com.leetai.purchasingagent.activity.PublishActivity;
import com.leetai.purchasingagent.adapter.PublishListAdapter;
import com.leetai.purchasingagent.modle.Publish;
import com.leetai.purchasingagent.tools.GsonTool;
import com.leetai.purchasingagent.tools.HttpTool;
import com.leetai.purchasingagent.tools.SharedPreferencesTool;
import com.leetai.purchasingagent.tools.ToastTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PublishListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    PublishListAdapter publishListAdapter;
    ListView lv_publish;
    List<Publish> list_publish = new ArrayList<Publish>();
    List<Map<String, Object>> listmap;
    HashMap<String, Object> map;
    Button btn_iwantpublish;


    public static PublishListFragment newInstance(String param1, String param2) {
        PublishListFragment fragment = new PublishListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PublishListFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_publish_list, container, false);
        init(view);
        getList();
        return view;
    }

    private void init(  View view ) {
        lv_publish = (ListView) view.findViewById(R.id.lv_publish);
        lv_publish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ToastTool.showToast(getActivity(),"test");
                Intent intent = new Intent(getActivity(),BidedListActivity.class);
               intent.putExtra("p_id",list_publish.get(position).getpId());

                startActivity(intent);
            }
        });
        btn_iwantpublish = (Button) view.findViewById(R.id.btn_iwantpublish);

        btn_iwantpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                intent.putExtra("type","add");
                //intent.putExtra("id","0");
                startActivityForResult(intent, 0);
            }
        });

    }

    private void getList() {

        SharedPreferencesTool.get(getActivity(), "userId", 0);
       // Log.i("获取userID=",SharedPreferencesTool.get(getActivity(),"userId",0)+"");
        String url = HttpTool.getUrl(SharedPreferencesTool.get(getActivity(),"userId",0)+"", "PublishListServlet");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(100);
        http.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Gson gson = new Gson();
                        list_publish = gson.fromJson(responseInfo.result, new TypeToken<List<Publish>>() {
                        }.getType());
                        //Log.i("list_publish=",list_publish.get()+"");
                        for (int i = 0; i < list_publish.size(); i++) {
                            Log.i("getTitle() =", list_publish.get(i).getpTitle()  );
                        }
                        listmap = new ArrayList<Map<String, Object>>();
                        for (int d = 0; d < list_publish.size(); d++) {
                            map = new HashMap<String, Object>();
                            map.put("tv_title", list_publish.get(d).getpTitle());
                            map.put("tv_description", list_publish.get(d).getpDescription());
                            map.put("tv_price", list_publish.get(d).getpPrice());
                            map.put("id", list_publish.get(d).getpId());
                            listmap.add(map);
                        }
                        publishListAdapter = new PublishListAdapter(getActivity(), listmap,list_publish,PublishListFragment.this);
                        lv_publish.setAdapter(publishListAdapter);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastTool.showToast(getActivity(), "连接失败，请检查网络连接！！！");

                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        getList();
    }
}
