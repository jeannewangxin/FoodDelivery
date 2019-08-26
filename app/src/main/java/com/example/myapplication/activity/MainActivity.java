package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.myapplication.R;
import com.example.myapplication.fragment.fragmentAccount;
import com.example.myapplication.fragment.fragmentHome;
import com.example.myapplication.fragment.fragmentSales;
import com.example.myapplication.fragment.fragmentSearch;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

    //use xUtils Outils Class
    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;
    @ViewInject (R.id.main_home)
    private RadioButton main_home;
    private FragmentManager fragmentManager;//管理fragment的类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        ViewUtils.inject (this);
        //初始化FragmentManager
        fragmentManager = getSupportFragmentManager ();
        //设置默认选中
        main_home.setChecked (true);
        group.setOnCheckedChangeListener (this);
        changeFragment (new fragmentHome (),false);
    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_home:
                changeFragment (new fragmentHome (),true);
                break;
            case R.id.main_account:
                changeFragment (new fragmentAccount (),true);
                break;
            case R.id.main_search:
                changeFragment (new fragmentSearch (),true);
                break;
            case R.id.main_tuan:
                changeFragment (new fragmentSales (),true);
                break;
        }

    }
    // 切换不同的fragment
    public void changeFragment(Fragment fragment,boolean isInit){
        //开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction ();
        transaction.replace (R.id.main_content,fragment);
        if(!isInit){
            transaction.addToBackStack (null);
        }
        transaction.commit ();
    }
}
