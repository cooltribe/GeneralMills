package com.searun.GIS.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.searun.GIS.R;
import com.searun.GIS.view.MyEditText;

/**
 * Created by 陈玉柱 on 2015/7/6.
 */
public class PersonDataFragment extends  Fragment{
    private String titleString;
    private TextView title;
    private MyEditText username;
    private MyEditText actualName;
    private MyEditText address;
    private MyEditText mobile;
    private Button config;

    @SuppressLint("ValidFragment")
    public PersonDataFragment(String titleString) {
        this.titleString = titleString;
    }

    public PersonDataFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_data,container,false);
        findView(view);
        return view;
    }

    private void findView(View view){
        title = (TextView) view.findViewById(R.id.title);
        title.setText(titleString);
        username = (MyEditText) view.findViewById(R.id.username);
        username.setText("用户名");
        actualName = (MyEditText) view.findViewById(R.id.actual_name);
        actualName.setText("真实姓名");
        address = (MyEditText) view.findViewById(R.id.address);
        address.setText("地址");
        mobile = (MyEditText) view.findViewById(R.id.mobile);
        mobile.setText("电话");
        config = (Button) view.findViewById(R.id.config_bt);
    }
}
