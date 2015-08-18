package com.searun.GIS.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.searun.GIS.R;
import com.searun.GIS.application.MyApplication;
import com.searun.GIS.utils.ImageUtil;
import com.searun.GIS.utils.L;
import com.searun.GIS.utils.SDCardUtils;
import com.searun.GIS.utils.T;
import com.searun.GIS.view.MyEditText;
import com.searun.GIS.view.SelectPicPopupWindow;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 陈玉柱 on 2015/7/6.
 */
public class FreezeRecordFragment extends  Fragment implements View.OnClickListener{

    private Calendar cal = Calendar.getInstance();// 系统时间
    private MyEditText start;
    private MyEditText end;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private StringBuffer sb;
    private ImageView freezeImageRecode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_freeze_recode,container,false);
        app = (MyApplication) getActivity().getApplication();
        findView(view);
        return view;
    }

    private void findView(View view){
        start = (MyEditText) view.findViewById(R.id.start);
        start.setCenterVisibility(View.VISIBLE);
        start.setRightVisibility(View.GONE);
        start.setText("开始打冷时间");
        start.setOnClickListener(this);

        end = (MyEditText) view.findViewById(R.id.end);
        end.setCenterVisibility(View.VISIBLE);
        end.setRightVisibility(View.GONE);
        end.setText("结束打冷时间");
        end.setOnClickListener(this);
        freezeImageRecode = (ImageView) view.findViewById(R.id.freeze_image_recode);
        freezeImageRecode.setOnClickListener(this);
    }
    private void getDialog(final MyEditText myEditText){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(),R.layout.date_time_dialog,null);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        builder.setView(view);
        cal.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth()));
                sb.append("  ");
                sb.append(timePicker.getCurrentHour())
                        .append(":").append(timePicker.getCurrentMinute());
                L.i(sb.toString());
                myEditText.setCenterText(sb.toString());
                dialog.cancel();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    private final String avatorpath = Environment.getExternalStorageDirectory() + "/xxx/avator/";
    private static final int CAMERA_REQUEST_CODE = 1000;
    private static final int PHOTO_REQUEST_CODE = 1001;
    private MyApplication app;
    private String sheariamgepath = ""; // 压缩后头像路径
    private String photographpath = ""; // 拍照完后头像路径

    /**
     * 手机拍照
     */
    private void getPhotoByCamere() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File fileDir = new File(avatorpath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();// 创建文件夹
            }
            photographpath = avatorpath + "qmimage" + new Date().getTime() + ".jpg";
            //鉴于三星手机拍照旋转后生命周期原因导致路径丢失，所以保存在application中
            app.photographpath = photographpath;
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photographpath)));
        }
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
    }

    /**
     * 重相册获取
     */
    private void getPhotoByPhoto(){
        if (!SDCardUtils.isSDCardEnable()) {
            T.showShort(getActivity(), getActivity().getResources().getString(R.string.msg_no_sdcard));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_CANCELED){
            ContentResolver resolver = getActivity().getContentResolver();
            String state = Environment.getExternalStorageState();
            Bitmap bm = null;
            L.i(requestCode + "");
            sheariamgepath = avatorpath + "qmimage" + new Date().getTime() + ".jpg";
            switch (requestCode){
                case CAMERA_REQUEST_CODE:
                    Log.i("settingok", "xxxxxxxxxxxxxxxx");
                    if (state.equals(Environment.MEDIA_MOUNTED)){
                        File fileDir = new File(avatorpath);
                        if ( !fileDir.exists()){
                            fileDir.mkdirs();//创建文件夹
                        }
                        if ("".equals(photographpath)) {
                            photographpath = app.photographpath;
                        }

                        app.sheariamgepath = sheariamgepath;
                        L.i("xxx", photographpath);
//                        freezeImageRecode.setImageBitmap(ImageUtil.getimage(photographpath, sheariamgepath, avatorpath));
                        freezeImageRecode.setImageBitmap(ImageUtil.rotateBitmap(ImageUtil.getimage(photographpath, sheariamgepath, avatorpath),ImageUtil.readPictureDegree(photographpath)));
                    } else {
                        T.showShort(getActivity(), getActivity().getResources().getString(R.string.camera_error));
                    }
                    break;
                case PHOTO_REQUEST_CODE:
                    try {
                        Uri uri = data.getData();
                        Log.i("settingok", uri.toString());

                        bm = MediaStore.Images.Media.getBitmap(resolver, uri);        //显得到bitmap图片

                        // 这里开始的第二部分，获取图片的路径：

                        String[] proj = {MediaStore.Images.Media.DATA};

                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = getActivity().managedQuery(uri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);
//                        freezeImageRecode.setImageBitmap(ImageUtil.getimage(path, sheariamgepath, avatorpath));
                        freezeImageRecode.setImageBitmap(ImageUtil.rotateBitmap(ImageUtil.getimage(path, sheariamgepath, avatorpath),ImageUtil.readPictureDegree(path)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }



    private void showOptionDialog() {
        final SelectPicPopupWindow dialog = new SelectPicPopupWindow(getActivity());
        dialog.setFirstButtonContent("拍照");
        dialog.setFirstButtonOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // CommonUtils.selectCameraPhone(cameraCode, resultPath,
                // AddNewDriverActivity.this);
                getPhotoByCamere();
                dialog.dismiss();
            }
        });
        dialog.setSecendButtonContent("从相册选择");
        dialog.setSecendButtonOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                CommonUtils.selectSystemPhone(PHOTO_REQUEST_CODE, getActivity());
               getPhotoByPhoto();
                dialog.dismiss();
            }
        });
        dialog.setThirdButtonContent("取消");
        dialog.setThirdButtonOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 显示窗口
        dialog.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
               getDialog(start);
                break;
            case R.id.end:
                getDialog(end);
                break;
            case R.id.freeze_image_recode:
                showOptionDialog();
                break;
        }
    }
}
