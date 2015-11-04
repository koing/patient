package com.greenpoint.patient.adapter;

import android.widget.BaseAdapter;

import com.greenpoint.patient.utils.AppUtil;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Administrator on 2015/10/19.
 */
public abstract class ArrayBaseAdapter extends BaseAdapter {

    public JSONArray data;


    public void bindData(JSONArray data){
        this.data = data;
    }
    @Override
    public int getCount() {
        if (AppUtil.isInvalidate(data)){
            return this.data.length();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        try{
            Object obj = data.get(position);
            return obj;
        }catch (JSONException e){

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
