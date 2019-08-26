package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends Activity {

    @ViewInject(R.id.welcome_guide_btn)
    private Button btn;
    @ViewInject(R.id.welcome_pager)
    private ViewPager pager;
    private List<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.welcome_guide);

        ViewUtils.inject (this);
        initViewPager ();
    }
    @OnClick (R.id.welcome_guide_btn)
    public void click(View view){
        startActivity (new Intent (getApplicationContext (), MainActivity.class));
        finish ();
    }

    //初始化ViewPager的方法
    public void initViewPager(){
        list = new ArrayList<View> ();

        ImageView iv = new ImageView (this);
        iv.setImageResource (R.drawable.guide_01);
        list.add (iv);

        ImageView iv1 = new ImageView (this);
        iv1.setImageResource (R.drawable.guide_02);
        list.add (iv1);

        ImageView iv2 = new ImageView (this);
        iv2.setImageResource (R.drawable.guide_03);
        list.add (iv2);

        pager.setAdapter (new myPagerAdapter());
        pager.setOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            //页卡被选中的方法
            @Override
            public void onPageSelected(int i) {
                if(i == 2){
                    btn.setVisibility (View.VISIBLE);
                }else {
                    btn.setVisibility (View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //定义ViewPager适配器
    class myPagerAdapter extends PagerAdapter{
        //计算需要多少item显示
        @Override
        public int getCount() {
            return list.size ();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        //初始化item实例化方法
        @Override
        public Object instantiateItem(ViewGroup container,int position){
            container.addView (list.get (position));
            return list.get (position);
        }

        @Override
        public void destroyItem(ViewGroup container,int position,Object object){
          //  super.destroyItem (container,position,object);
            container.removeView (list.get (position));
        }
    }



}
