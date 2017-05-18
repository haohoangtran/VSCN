package lachongmedia.vn.nfc;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;

/**
 * Created by tranh on 5/17/2017.
 */

public class Service extends android.app.Service {
    private static final String TAG = Service.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();


            }
        }, 0, 10000);
    }
}
