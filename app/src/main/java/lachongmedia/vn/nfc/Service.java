package lachongmedia.vn.nfc;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import lachongmedia.vn.nfc.database.realm.RealmDatabase;

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
