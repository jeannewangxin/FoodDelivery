package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MainActivity extends Activity {

    //use xUtils Outils Class
    @ViewInject(R.id.btn)
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        ViewUtils.inject (this);

    }
    @OnClick(R.id.btn)
    public void click(View view){
        Toast.makeText (MainActivity.this,"点击成功",Toast.LENGTH_LONG).show ();
    }
}
