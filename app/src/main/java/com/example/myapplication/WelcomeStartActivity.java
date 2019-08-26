package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.myapplication.util.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeStartActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_welcome);

        //延时跳转使用handler
      /*  new Handler (new Handler.Callback () {
            //处理接收到的消息的方法
            @Override
            public boolean handleMessage(Message msg) {
                //实现页面跳转
                startActivity (new Intent (getApplicationContext (),MainActivity.class));
                return false;
            }
        }).sendEmptyMessageDelayed (0,3000);//延时三秒进行任务执行

*/

      //使用定时器进行处理
        Timer timer = new Timer();
        timer.schedule (new Task (),3000);//定时器延时执行的方法
    }

    class Task extends TimerTask{

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            if(SharedUtils.getWelcomeBoolean (getBaseContext ())){
                startActivity (new Intent (getBaseContext (),MainActivity.class));
            }else {
                startActivity (new Intent (WelcomeStartActivity.this, WelcomeGuideActivity.class));
                SharedUtils.putWelcomeBoolean (getBaseContext (),true);
            }
            finish ();
        }
    }
}
