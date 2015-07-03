package com.searun.GIS.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.TextView;

import com.searun.GIS.R;
import com.searun.GIS.application.MyApplication;
import com.searun.GIS.fragment.MainFragment;
import com.searun.GIS.view.CircleImageView;

/**
 * Created by 陈玉柱 on 2015/7/2.
 */
public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private MyApplication app;
    private CircleImageView photoIcon;
    private TextView name;
    private TextView personData;
    private TextView carManage;
    private Button loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        findView();
        fragmentInit(1);
    }
    private void init(){
        fragmentManager = getSupportFragmentManager();
        app = (MyApplication) getApplication();
    }
    private void findView(){
        photoIcon = (CircleImageView) findViewById(R.id.icon);
        name = (TextView) findViewById(R.id.name);
        personData = (TextView) findViewById(R.id.person_data);
        carManage = (TextView) findViewById(R.id.car_manage);
        loginOut = (Button) findViewById(R.id.login_out);
    }
    private void fragmentInit(int userType){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (userType){
            case 1:
              ft.replace(R.id.fragment_right,new MainFragment(),"mainfragment");
                break;
            case 2:
                break;
        }
        ft.commitAllowingStateLoss();
    }
}
