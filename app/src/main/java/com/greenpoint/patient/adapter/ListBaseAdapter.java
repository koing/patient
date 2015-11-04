package com.greenpoint.patient.adapter;

import android.widget.BaseAdapter;

import com.greenpoint.patient.utils.AppUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public abstract class ListBaseAdapter<T> extends BaseAdapter {

    public List<T> data;

    public void bindData(List<T> data){
        this.data = data;
    }

    public int getCount(){
        if(AppUtil.isInvalidate(data)){
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
