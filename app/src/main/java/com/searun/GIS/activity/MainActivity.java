package com.searun.GIS.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.searun.GIS.R;
import com.searun.GIS.application.MyApplication;
import com.searun.GIS.fragment.MainFragment;
import com.searun.GIS.fragment.MenuRightFragment;

/**
 * Created by 陈玉柱 on 2015/7/2.
 */
public class MainActivity extends FragmentActivity implements MenuRightFragment.OnFragmentInteractionListener{

    private FragmentManager fragmentManager;
    private MyApplication app;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        findView();
        initEvents();
        fragmentInit(1);
    }
    private void initEvents(){
    mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            View mContent = mDrawerLayout.getChildAt(0);
            View mMenu = drawerView;
            float scale = 1 - slideOffset;
            float rightScale = 0.8f + scale * 0.2f;
            if (drawerView.getTag().equals("LEFT"))
            {

                float leftScale = 1 - 0.3f * scale;

                ViewHelper.setScaleX(mMenu, leftScale);
                ViewHelper.setScaleY(mMenu, leftScale);
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                ViewHelper.setTranslationX(mContent,
                        mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent,
                        mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                ViewHelper.setScaleX(mContent, rightScale);
                ViewHelper.setScaleY(mContent, rightScale);
            } else
            {
                ViewHelper.setTranslationX(mContent,
                        -mMenu.getMeasuredWidth() * slideOffset);
                ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                ViewHelper.setPivotY(mContent,
                        mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                ViewHelper.setScaleX(mContent, rightScale);
                ViewHelper.setScaleY(mContent, rightScale);
            }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            Log.i("drawer","open:" + drawerView.getTag());
            if (drawerView.getTag().equals("LEFT")){
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            } else if(drawerView.getTag().equals("RIGHT")){
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
            }

        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerLayout.setDrawerLockMode(
                    DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
            mDrawerLayout.setDrawerLockMode(
                    DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            Log.i("drawer", "drawer的状态：" + newState);
        }
    });
    }
    private void init(){
        fragmentManager = getSupportFragmentManager();
        app = (MyApplication) getApplication();
    }
    private void findView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
//        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
    }
    private void fragmentInit(int userType){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (userType){
            case 1:
              ft.replace(R.id.id_center_main,new MainFragment("订单中心"),"mainfragment");
                break;
            case 2:
                break;
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onFragmentInteraction(String string) {
        Log.i("hahha",string);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.id_center_main,new MainFragment(string),"mainfragment").commitAllowingStateLoss();
    }
}
