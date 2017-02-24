package com.bwie.monthly_testdemo.Application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * 类描述:
 * 作者：Administrator
 * 时间:2017/2/23 20:55
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        ImageUtils.initConfiguration(this);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
