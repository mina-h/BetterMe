package com.company.betterme;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.company.betterme.adapters.AdopterInputs.TAG;

public class AppBetterMe extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(TAG, "bbb");
    }
}
