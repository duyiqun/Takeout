package com.qun.takeout;

import android.app.Application;

/**
 * Created by Qun on 2017/6/17.
 */

public class MyApplication extends Application {

    private static MyApplication sMyApplication;

    public static MyApplication getInstance() {
        return sMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
    }
}
