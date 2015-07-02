package com.searun.GIS.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.searun.GIS.R;
import com.searun.GIS.application.MyApplication;
import com.searun.GIS.utils.ImageUtil;
import com.searun.GIS.utils.NetUtils;
import com.searun.GIS.utils.T;

import java.io.File;
import java.util.Date;


public class MainActivity extends Activity implements View.OnClickListener{

    private static final int CAMERA_REQUEST_CODE = 1000;
    private ImageView leftImage;
    private ImageView rightImage;
    private MyApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (MyApplication) getApplication();
        findView();

        Log.i("???",avatorpath);
    }

    private final String avatorpath = Environment.getExternalStorageDirectory() + "/xxx/avator/";

    private String sheariamgepath = ""; // 裁剪后头像路径
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED){
            ContentResolver resolver = getContentResolver();
            String state = Environment.getExternalStorageState();
            Bitmap bm = null;
            switch (requestCode){
                case CAMERA_REQUEST_CODE:
                    if (state.equals(Environment.MEDIA_MOUNTED)){
                        File fileDir = new File(avatorpath);
                        if ( !fileDir.exists()){
                            fileDir.mkdirs();//创建文件夹
                        }
                        if ("".equals(photographpath)) {
                            photographpath = app.photographpath;
                        }
                        sheariamgepath = avatorpath + "qmimage" + new Date().getTime() + ".jpg";
                        app.sheariamgepath = sheariamgepath;
                        leftImage.setImageBitmap(ImageUtil.getimage(photographpath, sheariamgepath, avatorpath));
                    } else {
                        T.showShort(this,getString(R.string.camera_error));
                    }
                    break;
                case 0:
                    Log.i("settingok","settingok");
                    break;
            }
        }
    }

    private void findView(){
       leftImage = (ImageView) findViewById(R.id.left_image);
       leftImage.setOnClickListener(this);

       rightImage = (ImageView) findViewById(R.id.right_image);
       rightImage.setOnClickListener(this);
   }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_image:
                getPhotoByCamere();
                break;
            case R.id.right_image:
//                Log.i("??", 1 + "" + NetUtils.isConnected(this) + "2," + NetUtils.isWifi(this));
                if ( !NetUtils.isConnected(this)) {
                    NetUtils.openSetting(this);
                }
                break;
        }
    }
}
