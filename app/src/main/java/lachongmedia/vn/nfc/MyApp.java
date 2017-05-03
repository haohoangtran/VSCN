package lachongmedia.vn.nfc;

import android.app.Application;
import android.util.Log;

import java.util.Date;

/**
 * Created by hao on 28/04/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Date date1=new Date();
        SharedPref.setInstance(getApplicationContext());
        Log.e("date", String.format("onCreate: %s", new Date()) );
        Log.e("date", String.format("onCreate: %s", Utils.getTime(date1,new Date())) );
    }
}
