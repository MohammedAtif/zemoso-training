package com.example.zemoso.myapplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zemoso on 11/11/16.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmconf=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmconf);
    }
}
