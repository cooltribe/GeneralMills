package com.searun.GIS.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import com.searun.GIS.R;

/**
 * Created by 陈玉柱 on 2015/7/7.
 */
public class CommonUtils {
    public static void selectSystemPhone(final int photoCode, Activity activity) {

        if (!SDCardUtils.isSDCardEnable()) {
            T.showShort(activity, activity.getResources().getString(R.string.msg_no_sdcard));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, photoCode);
    }
}
