package lachongmedia.vn.nfc;

import android.app.Application;

/**
 * Created by hao on 28/04/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPref.setInstance(getApplicationContext());
    }
}
