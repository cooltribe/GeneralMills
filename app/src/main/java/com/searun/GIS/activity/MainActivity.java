package com.searun.GIS.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.searun.GIS.R;


public class MainActivity extends Activity implements View.OnClickListener{

    private ImageView leftImage;
    private ImageView rightImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
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

                break;
            case R.id.right_image:

                break;
        }
    }
}
