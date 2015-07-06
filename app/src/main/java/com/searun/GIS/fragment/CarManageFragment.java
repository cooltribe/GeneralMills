package com.searun.GIS.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.searun.GIS.R;
import com.searun.GIS.view.MyEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarManageFragment extends Fragment {

    private String titleString;
    private TextView title;
    private MyEditText plateNumber;
    private MyEditText carType;
    private MyEditText carSize;
    private MyEditText carActualSize;
    private MyEditText carBoxCount;
    private MyEditText carOrderCount;
    private MyEditText carCustomCount;

    public CarManageFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CarManageFragment(String titleString) {
        this.titleString = titleString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_manage, container, false);
        findView(view);
        return view;
    }
    private void findView(View view){
        title = (TextView) view.findViewById(R.id.title);
        title.setText(titleString);
        plateNumber = (MyEditText) view.findViewById(R.id.plate_number);


        carType = (MyEditText) view.findViewById(R.id.car_type);

        carSize = (MyEditText) view.findViewById(R.id.car_size);

        carActualSize = (MyEditText) view.findViewById(R.id.car_actual_size);

        carBoxCount = (MyEditText) view.findViewById(R.id.car_box_count);

        carOrderCount = (MyEditText) view.findViewById(R.id.car_order_count);

        carCustomCount = (MyEditText) view.findViewById(R.id.car_custom_count);
    }

}
