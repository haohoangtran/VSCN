package lachongmedia.vn.nfc.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.adapters.PlaceAdapter;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.eventbus_event.TimeChangeEvent;

public class GroundActivity extends AppCompatActivity {
    private static final String TAG = GroundActivity.class.getSimpleName();
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_ground)
    TextView tvNameGround;
    @BindView(R.id.rv_places)
    RecyclerView rvPlaces;
    PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground);
        ButterKnife.bind(this);
        placeAdapter = new PlaceAdapter();
        rvPlaces.setAdapter(placeAdapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        tvName.setText(RealmDatabase.instance.getLoginRespon().getNhanvien().getTennhanvien());
        String name = getIntent().getExtras().getString("name");
        tvNameGround.setText("Mặt bằng: " + name);
        updateDisplay();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }
        }, intentFilter);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    private void updateDisplay() {
        Timer timer = new Timer();
        final Date date = RealmDatabase.instance.getDateStringStartFromRealm(SharedPref.instance.getIDUser());
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long minute = Utils.getTime(date, new Date());
                        if (minute < 60)
                            onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + minute + " phút"));
                        else {
                            onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + (int) minute / 60 + " giờ " + minute % 60 + " phút"));
                        }
                    }
                });

            }

        }, 0, 60000);
    }

    public void onTimeChange(TimeChangeEvent event) {
        tvTime.setText(event.getTime());
    }
}
