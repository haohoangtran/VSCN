package lachongmedia.vn.nfc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.eventbus_event.TimeChangeEvent;

public class GroundActivity extends AppCompatActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_ground)
    TextView tvNameGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground);
        ButterKnife.bind(this);
    }

    private void updateDisplay() {
        Timer timer = new Timer();
        final Date date = DbContext.instance.getDateStart();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // do UI updates here
                        onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + Utils.getTime(date, new Date()) + " phút"));
                    }
                });

            }

        }, 0, 60000);//Update text every 60 seconds
    }

    public void onTimeChange(TimeChangeEvent event) {
        tvTime.setText(event.getTime());
    }
}
