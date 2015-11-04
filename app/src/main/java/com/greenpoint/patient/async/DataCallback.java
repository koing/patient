package com.greenpoint.patient.async;

import org.json.JSONObject;

// 数据回调抽象类
public abstract class DataCallback {

    public abstract void onPreExecute();

    public abstract void onSuccess(JSONObject object);
}
