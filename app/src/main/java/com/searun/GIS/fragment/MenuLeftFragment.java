package com.searun.GIS.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.searun.GIS.R;
import com.searun.GIS.interfacepackage.OnFragmentInteractionListener;
import com.searun.GIS.view.CircleImageView;

/**
 * Created by 陈玉柱 on 2015/7/3.
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener{

    private TextView personData;
    private TextView carManage;
    private TextView name;
    private CircleImageView photo;
    private FragmentManager fm;
    private int userType;
    private FragmentTransaction ft;
    private OnFragmentInteractionListener mListener;

    @SuppressLint("ValidFragment")
    public MenuLeftFragment(int userType) {
        this.userType = userType;
    }

    public MenuLeftFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_menu,container,false);
        init();
        findView(view);
        return view;
    }

    private void init(){
        fm = getActivity().getSupportFragmentManager();
    }
    private void findView(View view){
        photo = (CircleImageView) view.findViewById(R.id.icon);
        name = (TextView) view.findViewById(R.id.name);
        personData = (TextView) view.findViewById(R.id.person_data);
        personData.setOnClickListener(this);
        carManage = (TextView) view.findViewById(R.id.car_manage);
        carManage.setOnClickListener(this);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
//        ft = fm.beginTransaction();
        switch (v.getId()){
            case R.id.car_manage:
                if (mListener != null) {
                    mListener.onFragmentInteraction("车辆管理");
                }
//                ft.replace(R.id.id_center_main, new CarManageFragment("车辆管理"), "车辆管理");
                break;
            case R.id.person_data:
                switch (userType){
                    case 1:
                        if (mListener != null) {
                            mListener.onFragmentInteraction("个人用户");
                        }
//                        ft.replace(R.id.id_center_main, new PersonDataFragment("个人用户"), "个人用户");
                        break;
                    case 2:
                        if (mListener != null) {
                            mListener.onFragmentInteraction("仓管用户");
                        }
//                        ft.replace(R.id.id_center_main, new StoreDataFragment("仓管用户"), "仓管用户");
                        break;
                }
                break;
        }
//        ft.commitAllowingStateLoss();
    }
}
