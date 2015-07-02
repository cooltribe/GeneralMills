package com.searun.GIS.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.searun.GIS.R;

public class MyEditText extends LinearLayout{

    private TextView left;
    private EditText right;

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public MyEditText(Context context) {
        super(context);
        init(context);
    }
    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.myview_edittext,this,false);
        left = (TextView) view.findViewById(R.id.left_tv);
        right = (EditText) view.findViewById(R.id.right_et);
    }

}
