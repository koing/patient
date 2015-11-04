package com.greenpoint.patient.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenpoint.patient.Constant.Constant;
import com.greenpoint.patient.R;
import com.greenpoint.patient.dialogs.PictureAlterDialog;
import com.greenpoint.patient.utils.AppUtil;
import com.greenpoint.patient.utils.ImageUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_sex)
    private TextView tvGender;

    @ViewInject(R.id.tv_phone)
    private TextView tvPhone;

    @ViewInject(R.id.tv_patient_id)
    private TextView tvId;

    @ViewInject(R.id.tv_name)
    private TextView tvName;

    @ViewInject(R.id.iv_dr_photo)
    private ImageView ivDrIcon;

    @ViewInject(R.id.ll_doctor_info)
    private LinearLayout llPhoto;

    @ViewInject(R.id.ll_changepwd)
    private LinearLayout llPwd;

    private PictureAlterDialog mAlterDialog;

    private Uri cropUri;

    private String portraitPath;

    private String theLarge;

    private File portraitFile;

    private Bitmap portraitBitmap;

    private Uri orgiUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        ViewUtils.inject(this);
        initView();
        tvTitle.setText(R.string.title_account_info);
    }


    private void initView(){
        llPhoto.setOnClickListener(this);


        llPwd.setOnClickListener(this);

        initDialog();
    }

    // 启动拍照
    private void startTakePhoto(){
        String str = "";
        if (Environment.getExternalStorageState().equals("mounted")){
            str = Constant.ComValue.IMAGE;
//            File file = new File(str);
//
//            if ((!file.exists())&&(!file.mkdirs())){
//                AppUtil.toastMessage("文件夹创建失败");
//                return;
//            }

            LogUtils.v("文件夹创建成功...");
        }
//        if (!AppUtil.isInvalidate(str)){
//            AppUtil.toastMessage("无法保存照片，请检查SD卡是否挂载");
//            return;
//        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        startActivityForResult(intent, 1);
    }

    // 开始选择图片
    private void startImagePick(){

        if (Build.VERSION.SDK_INT <19){
            LogUtils.v("当前版本小于19...");
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"选择图片"),2);
        }else{
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"选择图片"),2);
        }

    }

    // 图片剪裁
    private void startActionCrop(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");

        intent.putExtra("CROP",true);

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX",1);

        intent.putExtra("aspectY",1);

        // outputX outputY 是裁剪图片高度和宽度
        intent.putExtra("outputX",120);

        intent.putExtra("outputY",120);

        // 黑边
        intent.putExtra("scale",true);

        intent.putExtra("scaleUpIfNeeded",true);

        startActivityForResult(intent,0);
    }

    /**
     * 上传服务器更新图片
     */
    private void uploadNewPhoto(){


    }


    private Uri getUploadTempFile(Uri uri){

        if (Environment.getExternalStorageState().equals("mounted")){
            // 图片保存的路径
            File file = new File(Constant.ComValue.IMAGE);

            if (!file.exists()){
                file.mkdirs();
            }

            if (AppUtil.isInvalidate(ImageUtils.getAbsolutePathFromNoStandUri(uri))){
                ImageUtils.getAbsoluteImagePath(this,uri);
            }


        }

        return cropUri;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 0: // 图片裁剪完毕回调方法

                // 上传服务器更新图片
                uploadNewPhoto();
                break;

            case 1:

                break;
            case 2:
                LogUtils.v("选择图片回调结果.....");
                // 对图片进行剪裁
                LogUtils.v("当前URI地址为:" + data.getDataString());
                startActionCrop(data.getData());
                break;

            default:

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initDialog(){
        if (mAlterDialog == null){
            mAlterDialog = PictureAlterDialog.createDialog(this);
        }
        PictureAlterDialog.ivTakePhoto.setOnClickListener(this);
        PictureAlterDialog.ivFilePhoto.setOnClickListener(this);
        PictureAlterDialog.llDialog.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_doctor_info:
                // 弹出选择图片和拍摄的对话框
                mAlterDialog.show();
                break;

            case R.id.ll_changepwd:// 修改密码

                break;

            case R.id.ll_pic_dialog:


                mAlterDialog.dismiss();
                break;
            case R.id.tv_take_pic:// 开始拍照
                startTakePhoto();
                break;

            case R.id.tv_take_file:

                startImagePick();
                break;
            default:

                break;
        }
    }
}
