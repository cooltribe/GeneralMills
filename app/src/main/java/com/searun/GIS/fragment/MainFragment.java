package com.searun.GIS.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.searun.GIS.R;
import com.searun.GIS.view.CircleImageView;

public class MainFragment extends Fragment {

    private CircleImageView icon;
    private TextView title;
    private String titleString;

    @SuppressLint("ValidFragment")
    public MainFragment(String titleString) {
        this.titleString = titleString;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findView(view);
        return view;
    }

    private void findView(View view){
        icon = (CircleImageView) view.findViewById(R.id.icon);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(titleString);
    }

}
