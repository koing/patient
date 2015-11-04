package com.greenpoint.patient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.adapter.ArrayBaseAdapter;
import com.greenpoint.patient.async.DataCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.util.LogUtils;

import org.json.JSONObject;

/**
 * 记录fragment布局
 */
public class FgRecords extends BaseFragment implements AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener<ListView>{

    private PullToRefreshListView lvIndexRecords;

    // 设置注册listview
    private ListView lvReg;

    // 适配器
    private RecordAdapter mRecordAdapter;

    private PullToRefreshScrollView mScrollView;


    private int mCurrentStatus = 1;
    private DataCallback dataCallback = new DataCallback() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onSuccess(JSONObject object) {

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.v(">>>>> FgRecords onCreateView...");
        View view = View.inflate(getActivity(),R.layout.index_record,null);

        // 初始化控件
        initView(view);


        return view;
    }

    private void initView(View view){

        LogUtils.v("FgRecords initView...");
        // 下拉刷新listview
        lvIndexRecords = (PullToRefreshListView) view.findViewById(R.id.lv_index_records);

        // 设置监听事件
        lvIndexRecords.setOnRefreshListener(this);
        // 设置下拉刷新
        lvReg = lvIndexRecords.getRefreshableView();

        mRecordAdapter = new RecordAdapter();
        // 设置适配器
        lvReg.setAdapter(mRecordAdapter);

        // 设置子项点击事件
        lvReg.setOnItemClickListener(this);
        mScrollView = (PullToRefreshScrollView)view.findViewById(R.id.refresh_view);
        // 从上往下拉刷新
        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });

        //TODO 设置信息不可用显示
        test();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    private void test(){
        mScrollView.setVisibility(View.VISIBLE);
        lvIndexRecords.setVisibility(View.GONE);
    }

    private class RecordAdapter extends ArrayBaseAdapter{

        private RecordAdapter(){

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FgRecords.ViewHolder holder;
            View view = null;

            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(getActivity(),R.layout.record_item,null);
                holder.noinfo = (LinearLayout) convertView.findViewById(R.id.noinfo);

                convertView.setTag(holder);

            }else {
                view = convertView;

            }

            return view;
        }
    }

    static class ViewHolder{

        LinearLayout noinfo;

    }
}
