package com.example.myapplication.util;


import android.content.Context;
import android.content.SharedPreferences.Editor;


//实现标记的写入与读取
public class SharedUtils {
    private static final String FILE_NAME = "FoodDelivery";
    private static final String MODE_NAME = "welcome";

    //获取boolean类型的值
    public static boolean getWelcomeBoolean(Context context){
        return context.getSharedPreferences (FILE_NAME,context.MODE_PRIVATE).getBoolean (MODE_NAME,false);
    }

    //写入boolean类型的值
    public static void putWelcomeBoolean(Context context,boolean isFirst){
        Editor editor = context.getSharedPreferences (FILE_NAME,Context.MODE_APPEND).edit ();
        editor.putBoolean (MODE_NAME,isFirst);
        editor.commit ();
    }

    public static void putCityName(Context context,String cityName){
        Editor editor = context.getSharedPreferences (FILE_NAME,Context.MODE_APPEND).edit ();
        editor.putString ("cityName",cityName);
        editor.commit ();
    }

    public static String getCityName(Context context){
        return context.getSharedPreferences (FILE_NAME,Context.MODE_PRIVATE).getString ("cityName","选择城市");
    }
}
