package com.greenpoint.patient.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.activity.BaiduLocationActivity;
import com.greenpoint.patient.activity.HospitalActivity;
import com.greenpoint.patient.adapter.ListBaseAdapter;
import com.greenpoint.patient.utils.AppUtil;
import com.greenpoint.patient.utils.BaseUtil;
import com.greenpoint.patient.utils.DialogUtil;
import com.greenpoint.patient.view.GridViewForScroll;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 医院fragment
 */
public class FgHospitals extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    private ScrollView scrollView;

    private int currentItem;

    private ViewPager mViewPager;

    private LinearLayout llPoints;

    // 表格适配器
    private GridAdapter mGridAdapter;

    private GridViewForScroll gvMain;

    private ArrayList<ImageView> imageViewList;

    private ArrayList<ImageView> imagePoints;

    private ImageView ivCurrentPoint;
    boolean isStop = false;

    private Handler mHandler = new Handler();

    private Timer timer = new Timer();

    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (FgHospitals.this.imageViewList != null) {
                FgHospitals.this.mViewPager.setCurrentItem(1 + FgHospitals.this.mViewPager.getCurrentItem());
            }
            if (!FgHospitals.this.isStop) {
                // 延迟5秒执行
                FgHospitals.this.mHandler.postDelayed(mImageTimerTask, 5000L);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.index_hospital, null);
        scrollView = (ScrollView) view.findViewById(R.id.hsp_scrollview);

        initActionBar();
//        initView(view);
//        initViewPager(view);
        initGridView(view);

        updateGridView();
//        onRefreshView();

        scrollToTop();
        return view;
    }

    private void scrollToTop(){
        LogUtils.v(">>>>> FgHospitals scrollToTop...");
        if(scrollView != null){
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
            scrollView.smoothScrollTo(0,0);
        }
    }

    private void updateGridView(){

        LogUtils.v("FgHospitals UpdateGridView... ");
//        ArrayList<String[]> arrayList = new ArrayList<>();
//        for(int i = 0;i<7;i++){
//            String[] mArray = new String[5];
//            mArray[0] = "预约挂号";
//            mArray[1] = "icon";
//            mArray[2] = "enable";
//            mArray[3] = "code";
//            mArray[4] = "url";
//
//            arrayList.add(mArray);
//        }

        this.mGridAdapter.bindData(getDefaultGridData());
        mGridAdapter.notifyDataSetChanged();

    }

    private void initGridView(View view){
        mGridAdapter = new GridAdapter();

        gvMain = (GridViewForScroll)view.findViewById(R.id.gv_main);

        gvMain.setAdapter(mGridAdapter);

        gvMain.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        LogUtils.v("FgHospitals onStart...");
        super.onStart();
    }


    @Override
    public void onResume() {
        LogUtils.v("FgHospitals onResume...");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.v("FgHospitals onPause...");
        super.onPause();
    }

    private void initActionBar() {
        LinearLayout layout = (LinearLayout)getActivity().findViewById(R.id.ll_hospital_choice);
        (getActivity().findViewById(R.id.ib_title_left)).setOnClickListener(this);
        layout.setOnClickListener(this);
    }

    private void initView(View view) {
        LogUtils.v("图片开始轮转...");
//        mHandler.postDelayed(delayShow,5000L);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 100;
                mHandler.sendMessage(msg);
            }
        }, 0, 500);
    }


    private void updateViewPager(){
        imageViewList = getDefaultViewPager();
    }

    private void initViewPager(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.vp_image);
        llPoints = (LinearLayout) view.findViewById(R.id.ll_points);

        // 实例化
        imagePoints = new ArrayList();
        // 实例化
        imageViewList = new ArrayList();

        //
        startImageTimerTask();

        // 监听viewpager的触摸事件
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                switch (event.getAction()) {
//                    case 1:
//
//                    default:
//                        stopImageTimerTask();
//                        break;
//                }
                return false;
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    startImageTimerTask();
                }
            }

            @Override
            public void onPageSelected(int position) {
                int i = position % FgHospitals.this.imageViewList.size();
                if (AppUtil.isInvalidate(FgHospitals.this.imagePoints)) {
                    ivCurrentPoint.setSelected(false);
                    imagePoints.get(i).setSelected(true);
                    ivCurrentPoint = imagePoints.get(i);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    public void onRefreshView()  {
        LogUtils.i("FgHospitals onRefreshView...");

        String str = "南京鼓楼医院";

        DialogUtil.startProgressDialog(getActivity());

        if (AppUtil.isInvalidate(str)){
//            updateViewPager();

            updateGridView();

            this.mGridAdapter.bindData(getDefaultGridData());
            mGridAdapter.notifyDataSetChanged();
        }

    }

    private void startImageTimerTask() {
        stopImageTimerTask();
        mHandler.postDelayed(mImageTimerTask, 5000L);
    }


    private void stopImageTimerTask() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private ArrayList<String[]> getDefaultGridData(){
        ArrayList localArrayList = new ArrayList();
        Resources resources = getResources();
        String[] str1 = new String[5];
        str1[0] = resources.getString(R.string.title_app_reg);
        str1[1] = "drawable://" + String.valueOf(R.drawable.grid_app_reg);
        str1[2] = "0";
        str1[3] = "FUNC_REG";
        str1[4] = "url";
        localArrayList.add(str1);

        String[] str2 = new String[5];
        str2[0] = resources.getString(R.string.title_exam);
        str2[1] = "drawable://" + String.valueOf(R.drawable.grid_exam);
        str2[2] = "0";
        str2[3] = "FUNC_CHECK";
        str2[4] = "url";
        localArrayList.add(str2);

        String[] str3 = new String[5];
        str3[0] = resources.getString(R.string.title_consult_doctor);
        str3[1] = "drawable://" + R.drawable.grid_consult;
        str3[2] = "0";
        str3[3] = "FUNC_CONSULT_DOCTOR";
        str3[4] = "url";
        localArrayList.add(str3);

        String[] str4 = new String[5];
        str4[0] = resources.getString(R.string.title_hospital_intr);
        str4[1] = "drawable://" + R.drawable.grid_hospital_intr;
        str4[2] = "0";
        str4[3] = "FUNC_HOSPITAL";
        str4[4] = "url";
        localArrayList.add(str4);

        String[] str5 = new String[5];
        str5[0] = resources.getString(R.string.title_dr_list);
        str5[1] = "drawable://" + R.drawable.grid_doctors;
        str5[2] = "0";
        localArrayList.add(str5);

        String[] str6 = new String[5];
        str6[0] = resources.getString(R.string.title_queue);
        str6[1] = "drawable://" + R.drawable.grid_queue;
        str6[2] = "0";
        localArrayList.add(str6);

        String[] str7 = new String[5];
        str7[0] = resources.getString(R.string.title_triage);
        str7[1] = "drawable://" + R.drawable.grid_triage;
        str7[2] = "0";
        localArrayList.add(str7);

        String[] str8 = new String[5];
        str8[0] = resources.getString(R.string.title_announcement);
        str8[1] = "drawable://" + R.drawable.grid_announcement;
        str8[2] = "0";
        localArrayList.add(str8);

        String[] str9 = new String[3];
        str9[0] = resources.getString(R.string.title_take_reports);
        str9[1] = "drawable://" + R.drawable.grid_reports;
        str9[2] = "0";
        localArrayList.add(str9);

        String[] str10 = new String[5];
        str10[0] = resources.getString(R.string.title_guide);
        str10[1] = "drawable://" + R.drawable.grid_guide;
        str10[2] = "0";
        localArrayList.add(str10);

        String[] str11 = new String[5];
        str11[0] = resources.getString(R.string.title_health_pedia);
        str11[1] = "drawable://" + R.drawable.grid_health_pedia;
        str11[2] = "0";
        localArrayList.add(str11);



        return localArrayList;
    }

    private ArrayList<ImageView> getDefaultViewPager(){
        ArrayList imgList = new ArrayList();
        for(int i = 0;i<2;i++){
            ImageView imageView = new ImageView(getActivity());
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            // 填满父布局
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            // 宽度填满父布局
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            // 设置imageview参数
            imageView.setLayoutParams(params);

            String uri = ImageDownloader.Scheme.ASSETS.wrap("title_" + i + ".png");

            ImageLoader.getInstance().displayImage(uri,imageView, AppUtil.getImageOptions(R.drawable.hospital_default_ad));

            imgList.add(imageView);
        }
        return imgList;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()){
            case R.id.ib_title_left:// 点击定位按钮

//                intent.setClass(getActivity(), LocationChoiceActivity.class);
                intent.setClass(getActivity(), BaiduLocationActivity.class);
                BaseUtil.animStartActivity(getActivity(),intent);
                break;
            default:

                choiceLocation();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] params = (String[]) parent.getItemAtPosition(position);

        // 当前模块名称
        String model = params[3];

        // 当前模块URL
        String url = params[4];
        Intent intent = choiceFunction(model,new Intent(),url);

        BaseUtil.animStartActivity(getActivity(),intent);
    }

    /**
     * 模块选择
     * @param modelName
     * @param intent
     * @param url
     * @return
     */
    private Intent choiceFunction(String modelName,Intent intent,String url){

        if ("FUNC_HOSPITAL".equals(modelName)){// 医院介绍
            intent.setClass(getActivity(), HospitalActivity.class);
        }

        return intent;
    }


    private void choiceLocation(){

    }

    // 表格布局适配器  最终继承BaseAdapter
    private class GridAdapter extends ListBaseAdapter<String[]> {

        // 无参构造函数
        private GridAdapter(){

        }


        // 每次出现子布局的时候回调此方法
        // 注意：convertView中缓存已经出现过的布局
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            String[] arrayOfStrings;

            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.gridview_item_pager,null);
                // 缓存各个控件的实例
                holder = new ViewHolder();

                holder.ivImage = (ImageView)convertView.findViewById(R.id.iv_image);

                holder.ivFuncEnable = (ImageView)convertView.findViewById(R.id.iv_func_enable);

                holder.tvWords = (TextView)convertView.findViewById(R.id.tv_words);

                holder.word = getResources().getString(R.string.hospital_null);

                convertView.setTag(holder);

                arrayOfStrings =this.data.get(position);

                String str = arrayOfStrings[0];

                if(str.equals(holder.word)){
                    holder.tvWords.setText("");
                    holder.ivImage.setVisibility(View.INVISIBLE);
                }else{
                    holder.ivImage.setVisibility(View.VISIBLE);
                    holder.tvWords.setText(str);
                }


                ImageLoader.getInstance().displayImage(arrayOfStrings[1], holder.ivImage, AppUtil.getImageOptions(0));

                if ("0".equals(arrayOfStrings[2])){
                    holder.ivFuncEnable.setVisibility(View.GONE);

                }else{
                    holder.ivFuncEnable.setVisibility(View.VISIBLE);
                }

            }

            return convertView;
        }
    }

    // 内部类用来缓存控件实例
    static class ViewHolder{

        ImageView ivImage;

        String word;


        TextView tvWords;

        // 功能暂未开通
        ImageView ivFuncEnable;
    }
}
