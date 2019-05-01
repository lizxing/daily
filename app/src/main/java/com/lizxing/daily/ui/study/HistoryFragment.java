package com.lizxing.daily.ui.study;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizxing.daily.R;
import com.lizxing.daily.common.DailyFragment;
import com.lizxing.daily.gson.History;
import com.lizxing.daily.gson.HistoryList;
import com.lizxing.daily.items.HistoryItem;
import com.lizxing.daily.utils.HttpUtil;
import com.lizxing.daily.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HistoryFragment extends DailyFragment {

    private static final String TAG = "===HistoryFragment";
    private Toolbar toolbar;
    private HistoryItemAdapter historyItemAdapter;
    private RecyclerView recyclerView;
    private List<HistoryItem> itemList = new ArrayList<HistoryItem>();

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //标题栏
        toolbar = view.findViewById(R.id.toolbar_english);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(HistoryFragment.this);
                fragmentTransaction.commit();
            }
        });
    }

    private void initData(){
        requestHistory();

    }

    /**
     * 发起请求获取历史
     */
    private void requestHistory(){
        String historyAddress = getAddress();
        HttpUtil.sendOkHttpRequest(historyAddress, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //获取到的数据
                final HistoryList historyList = Utility.handleHistoryResponse(responseText);
                final int code = historyList.code;
                final String msg = historyList.msg;


                if (code == 200){
                    for (History history:historyList.historyList){
                        HistoryItem item = new HistoryItem(history.title,history.date);
                        itemList.add(item);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //适配器
                            historyItemAdapter = new HistoryItemAdapter(itemList, getContext());
                            recyclerView.setAdapter(historyItemAdapter);
                            historyItemAdapter.notifyDataSetChanged();
                        };
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getActivity().getApplicationContext(), "返回数据错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(), "历史请求响应失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 获取请求地址
     */
    private String getAddress(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String m = month<10?"0"+month : ""+month;
        String d = day<10?"0"+day : ""+day;
        String address = "http://api.tianapi.com/txapi/lishi/?key=6634dbe82d9bddbf27123652cff14e0b&date="+m+d;
        Log.d(TAG,address);
        return address;
    }
}
