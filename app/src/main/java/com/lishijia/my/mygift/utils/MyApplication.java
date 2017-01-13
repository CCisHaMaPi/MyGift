package com.lishijia.my.mygift.utils;

import android.app.Application;

/**
 * Created by my on 2017/1/3.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ImageUtils.initCache(getApplicationContext());
    }
}
