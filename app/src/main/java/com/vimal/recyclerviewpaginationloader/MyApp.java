/**
 * Created by Vimal on March-2021.
 */
package com.vimal.recyclerviewpaginationloader;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import io.realm.Realm;


public class MyApp extends MultiDexApplication {

    private static MyApp myapp;

    public static MyApp getInstance() {
        return myapp;
    }

    public static Context getContext() {
        return myapp.getApplicationContext();
    }

    public static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        myapp = this;
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
    }


}