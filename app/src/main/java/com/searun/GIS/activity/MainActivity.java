package com.searun.GIS.activity;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.searun.GIS.R;
import com.searun.GIS.application.MyApplication;
import com.searun.GIS.utils.FileUtils;
import com.searun.GIS.utils.ImageUtil;
import com.searun.GIS.utils.L;
import com.searun.GIS.utils.SDCardUtils;
import com.searun.GIS.utils.T;
import com.searun.GIS.view.SelectPicPopupWindow;

public class MainActivity extends Activity {

    private final String avatorpath = Environment.getExternalStorageDirectory() + "/xxx/avator/";
    private static final int CAMERA_REQUEST_CODE = 1000;
    private static final int PHOTO_REQUEST_CODE = 1001;
    private MyApplication app;
    private String sheariamgepath = ""; // 压缩后头像路径
    private String photographpath = ""; // 拍照完后头像路径
    public static final String TAG = "MainActivity";
    ValueCallback<Uri> mUploadMessage;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.setBackgroundColor(Color.WHITE);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().supportMultipleWindows();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        // 扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(
                LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.clearView();
        mWebView.setWebChromeClient(new MyWebChromeClient());

        mWebView.setWebViewClient(new MyWebViewClient(this));
//		webView.loadUrl("file:///android_asset/upload_image.html");
        mWebView.loadUrl("http://58.213.134.205:9965/keeper/login.html");
    }


    private class MyWebViewClient extends WebViewClient {
        private Context mContext;

        public MyWebViewClient(Context context) {
            super();
            mContext = context;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "URL地址:" + url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished");
            super.onPageFinished(view, url);
        }
    }


    private class MyWebChromeClient extends WebChromeClient {

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (mUploadMessage != null) return;
            mUploadMessage = uploadMsg;
            showOptionDialog();
//            selectImage();
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

    }

    /**
     * 检查SD卡是否存在
     *
     * @return
     */
    public final boolean checkSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!flag) {
            Toast.makeText(this, "请插入手机存储卡再使用本功能", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }
    protected final void selectImage() {
        if (!checkSDcard())
            return;
        String[] selectPicTypeStr = { "camera","photo" };
        new AlertDialog.Builder(this)
                .setItems(selectPicTypeStr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    // 相机拍摄
                                    case 0:
//                                        openCarcme();
                                        break;
                                    // 手机相册
                                    case 1:
//                                        chosePic();
                                        break;
                                    default:
                                        break;
                                }
//                                compressPath = Environment
//                                        .getExternalStorageDirectory()
//                                        .getPath()
//                                        + "/fuiou_wmp/temp";
//                                new File(compressPath).mkdirs();
//                                compressPath = compressPath + File.separator
//                                        + "compress.jpg";
                            }
                        }).show();
    }


    private void showOptionDialog() {
        final SelectPicPopupWindow dialog = new SelectPicPopupWindow(this);
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
        dialog.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

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
            cameraUri = Uri.fromFile(new File(photographpath));
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        }
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
    }

    /**
     * 重相册获取
     */
    private void getPhotoByPhoto() {
        if (!SDCardUtils.isSDCardEnable()) {
            T.showShort(this, getResources().getString(R.string.msg_no_sdcard));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }


    Uri cameraUri;

    /**
     * 拍照结束后
     */
    private void afterOpenCamera(String finalPath) {
        File f = new File(photographpath);
        addImageGallery(f);
        File newFile = FileUtils.compressFile(f.getPath(), finalPath);
    }

    /**
     * 解决拍照后在相册中找不到的问题
     */
    private void addImageGallery(File file) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }



    /**
     * 返回文件选择
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (null == mUploadMessage) return;
        Uri uri = null;
        if (resultCode != RESULT_CANCELED) {
            ContentResolver resolver = getContentResolver();
            String state = Environment.getExternalStorageState();
            Bitmap bm = null;
            L.i(requestCode + "");
            sheariamgepath = avatorpath + "qmimage" + new Date().getTime() + ".jpg";
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    Log.i("settingok", "xxxxxxxxxxxxxxxx");
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File fileDir = new File(avatorpath);
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();//创建文件夹
                        }
                        if ("".equals(photographpath)) {
                            photographpath = app.photographpath;
                        }

                        app.sheariamgepath = sheariamgepath;
                        L.i("xxx", photographpath);
                        afterOpenCamera(sheariamgepath);
                        uri = cameraUri;
//                        freezeImageRecode.setImageBitmap(ImageUtil.getimage(photographpath, sheariamgepath, avatorpath));
//                        freezeImageRecode.setImageBitmap(ImageUtil.rotateBitmap(ImageUtil.getimage(photographpath, sheariamgepath, avatorpath), ImageUtil.readPictureDegree(photographpath)));
                    } else {
                        T.showShort(this, getResources().getString(R.string.camera_error));
                    }
                    break;
                case PHOTO_REQUEST_CODE:
                    try {
                        uri = afterChosePic(intent,bm,resolver,sheariamgepath);
//                        freezeImageRecode.setImageBitmap(ImageUtil.getimage(path, sheariamgepath, avatorpath));
//                        freezeImageRecode.setImageBitmap(ImageUtil.rotateBitmap(ImageUtil.getimage(path, sheariamgepath, avatorpath),ImageUtil.readPictureDegree(path)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            mUploadMessage.onReceiveValue(uri);
            mUploadMessage = null;
        }
    }

    private Uri afterChosePic(Intent intent, Bitmap bm, ContentResolver resolver,String finalPath) {

        Uri uri = intent.getData();
        Log.i("settingok", uri.toString());

        try {
            bm = MediaStore.Images.Media.getBitmap(resolver, uri);        //显得到bitmap图片

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 这里开始的第二部分，获取图片的路径：

        String[] proj = {MediaStore.Images.Media.DATA};

        //好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = this.managedQuery(uri, proj, null, null, null);
        //按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        //最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        if (path != null && (path.endsWith(".png") || path.endsWith(".PNG") || path.endsWith(".jpg") || path.endsWith(".JPG"))) {
            File newFile = FileUtils.compressFile(path, finalPath);
            return Uri.fromFile(newFile);
        } else {
            Toast.makeText(this, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
