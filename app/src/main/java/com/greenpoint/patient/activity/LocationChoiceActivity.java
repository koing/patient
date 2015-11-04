package com.greenpoint.patient.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.adapter.ArrayBaseAdapter;
import com.greenpoint.patient.async.AsyncCityQuery;
import com.greenpoint.patient.async.DataCallback;
import com.greenpoint.patient.utils.AppUtil;
import com.greenpoint.patient.utils.HttpHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONObject;


public class LocationChoiceActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    // 省份listview
    @ViewInject(R.id.lv_location_level1)
    private ListView lvProvince;

    // 城市listview
    @ViewInject(R.id.lv_location_level2)
    private ListView lvCity;

    // 城市适配器
    private CityAdapter cityAdapter;

    // 省份适配器
    private CityAdapter provinceAdapter;

    private JSONArray lstProvince;

    DataCallback dataCallback = new DataCallback() {
        @Override
        public void onPreExecute() {
            startProgressDialog();
        }

        @Override
        public void onSuccess(JSONObject object) {

            if ("0".equals(HttpHelper.getReturnCode())) {
                lstProvince = object.optJSONArray("data");
                if (AppUtil.isInvalidate(lstProvince)) {
                    provinceAdapter.bindData(lstProvince);
                    provinceAdapter.notifyDataSetChanged();
                    lvProvince.performItemClick(lvProvince.getAdapter().getView(0, null, null), 0, lvProvince.getItemIdAtPosition(0));
                }
            }else{
                // 停止进度对话框
                stopProgressDialog();
                // 显示错误信息
                setNoInfoView();
            }
        }
    };


    private void setNoInfoView(){
        lvProvince.setVisibility(View.GONE);
        lvCity.setVisibility(View.GONE);
        findViewById(R.id.noinfo).setVisibility(View.VISIBLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location_choice);
        ViewUtils.inject(this);

        tvTitle.setText(R.string.title_location_list);

        // 初始化数据
        initData();

        // 初始化View
        initView();


    }

    private void initData() {
        new AsyncCityQuery(dataCallback).execute(new String[0]);
    }

    private void initView() {

        lvProvince.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        provinceAdapter = new CityAdapter(R.id.lv_location_level1, R.id.tv_dept__level1_name);

        cityAdapter = new CityAdapter(R.id.lv_location_level2, R.id.tv_dept__level2_name);

        lvProvince.setAdapter(provinceAdapter);

        lvCity.setAdapter(cityAdapter);

        // 对省份和城市listview设置监听事件
        lvCity.setOnItemClickListener(this);

        lvProvince.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class CityAdapter extends ArrayBaseAdapter {
        private int itemId;

        private int textViewId;

        public CityAdapter(int itemId, int textViewId) {
            this.itemId = itemId;
            this.textViewId = textViewId;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            View view;

            if (convertView == null) {

                holder = new ViewHolder();
                view = LayoutInflater.from(LocationChoiceActivity.this).inflate(itemId, null);
                // 定位失败
                holder.failed = LocationChoiceActivity.this.getResources().getString(R.string.location_fail);
                // 当前定位
                holder.location = LocationChoiceActivity.this.getResources().getString(R.string.current_lcation);

                holder.textView = (TextView) view.findViewById(textViewId);
                view.setTag(holder);

            } else {
                view = convertView;

                holder = (ViewHolder) view.getTag();

            }
            return view;
        }
    }


    class ViewHolder {
        String failed;

        String location;

        TextView textView;
    }
}
