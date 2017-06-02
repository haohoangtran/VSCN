package lachongmedia.vn.nfc;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import net.gotev.uploadservice.UploadService;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lachongmedia.vn.nfc.database.realm.realm_models.DateString;

/**
 * Created by hao on 28/04/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
//        Realm.deleteRealm(realmConfiguration); // Clean slate
//        Realm.setDefaultConfiguration(realmConfiguration);
        SharedPref.setInstance(getApplicationContext());
//        Intent intent= new Intent(this,Service.class);
//        startService(intent);
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "lachongmedia.vn.nfc";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
