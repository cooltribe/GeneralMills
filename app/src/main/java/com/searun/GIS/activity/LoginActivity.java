package com.searun.GIS.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.searun.GIS.R;

public class LoginActivity extends Activity implements View.OnClickListener{

    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
    }

    private void findView(){
        register = (Button) findViewById(R.id.register_bt);
        register.setOnClickListener(this);
        login = (Button) findViewById(R.id.login_bt);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.register_bt:
                intent = new Intent(this,RegisterActivity.class);
                break;
            case R.id.login_bt:
                intent = new Intent(this,MainActivity2.class);
                break;
        }
        startActivity(intent);
    }
}
