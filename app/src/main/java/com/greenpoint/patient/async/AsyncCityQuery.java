package com.greenpoint.patient.async;

import android.os.AsyncTask;

import com.greenpoint.patient.MyApplication;
import com.greenpoint.patient.R;
import com.greenpoint.patient.bean.BeanInfo;

import org.json.JSONArray;
import org.json.JSONObject;


public class AsyncCityQuery extends AsyncTask<String,R.integer,JSONArray>{

    private DataCallback callback;

    public AsyncCityQuery(DataCallback callback){
        this.callback = callback;
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        // 当前位置
        String currentLocation = MyApplication.getInstance().getResources().getString(R.string.current_lcation);

        // 医院所在的省份
        String hospitalProvice = MyApplication.getInstance().getResources().getString(R.string.hospital_province);

        JSONObject object = BeanInfo.getJsonObject("area.list",new String[]{"area",""});

        return null;
    }

    @Override
    protected void onPreExecute() {
        this.callback.onPreExecute();

    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        JSONObject object = new JSONObject();

        try{
            object.put("data",jsonArray);

            this.callback.onSuccess(object);
        }catch (Exception e){

        }

    }
}
