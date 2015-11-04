package com.greenpoint.patient.bean;

import com.greenpoint.patient.utils.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class BeanInfo {

    public static JSONObject getJsonObject(String str,Object[] obj){
        Object object = new JSONObject();

        JSONObject localObject1 = new JSONObject();

        JSONObject localObject2 = new JSONObject();

        int i = 0;

        Object object1;

        try {
            while (i < obj.length){
                if (i + 1 < obj.length){
                    localObject2.put((String)obj[1],obj[i+1]);
                    i += 2;

                }else{
                    throw new ArrayIndexOutOfBoundsException("传入的数组元素个数需成对出现");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            localObject2 = null;
        }

        if ("705".equals(HttpHelper.getReturnCode())){

        }

        try {
            localObject1.put("o",str);
            localObject1.put("p",localObject2);

            JSONObject localObject3 = HttpHelper.onSuccess(localObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
