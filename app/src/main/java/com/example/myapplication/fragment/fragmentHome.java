package com.example.myapplication.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.SharedUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

public class fragmentHome extends Fragment implements LocationListener {
    @ViewInject(R.id.index_top_city)
    private TextView topCity;
    private String cityName;//当前城市名称
    private LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate (R.layout.home_index, null);
        ViewUtils.inject (this, view);
        //获取数据并显示
        topCity.setText(SharedUtils.getCityName (getActivity ()));
        return view;

    }

    @Override
    public void onStart() {
        super.onStart ();
        //检查当前gps模块
        checkGPSIsOpen ();
    }

    private void checkGPSIsOpen() {
        //获取当前的LoactionManager对象
        locationManager = (LocationManager) getActivity ().getSystemService (Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER);
        if (!isOpen) {
            //进入gps设置页面
            Intent intent = new Intent ();
            intent.setAction (Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult (intent, 0);
        }
        //开始定位
        startLocation ();
    }

    private void startLocation() {
        if (ActivityCompat.checkSelfPermission (getActivity (), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (getActivity (), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 2000, 10, this);
    }

    private Handler handler = new Handler (new Handler.Callback () {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 1){
                topCity.setText(cityName);
            }
            return false;
        }
    });

    //获取对应位置的经纬度并且定位城市
    private void updateWithNewLocation(Location location){
        double lat = 0.0, lng = 0.0;
        if(location!=null){
            lat = location.getLatitude ();
            lng = location.getLongitude ();
            Log.i("TAG","经度是:"+lat+",纬度是："+lng);
        }else {
            cityName = "无法获取城市信息";

        }
        //通过经纬度来获取地址，由于地址会有多个，和经纬度精确度相关。
        //本实例中定义了最大返回数2，即在集合对象中有两个值
        List<Address> list = null;
        Geocoder geocoder = new Geocoder (getActivity ());
        try{
           list = geocoder.getFromLocation (lat,lng,2);
        }catch (Exception e){
            e.printStackTrace ();
        }
        if(list!=null && list.size ()>0){
            for(int i=0;i<list.size ();i++){
                Address address = list.get (i);
                cityName = address.getLocality ();//获取城市
            }
        }
        handler.sendEmptyMessage (1);
    }
    /**
     * 位置信息更改执行的方法
     * Called when the location has changed.
     *
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        //更新当前的位置信息
        updateWithNewLocation(location);
    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        //保存城市
        SharedUtils.putCityName (getActivity (),cityName);

        //停止定位
        stopLoaction ();
    }

    //停止定位
    private void stopLoaction(){
        locationManager.removeUpdates (this);
    }
}
