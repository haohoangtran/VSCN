package lachongmedia.vn.nfc.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.models.Member;
import lachongmedia.vn.nfc.database.models.WC;
import lachongmedia.vn.nfc.eventbus_event.TimeChangeEvent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final String[][] techList = new String[][]{
            new String[]{
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };
    @BindView(R.id.bt_main)
    AppCompatButton btMain;
    @BindView(R.id.bt_action)
    AppCompatButton btAction;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time)
    TextView tvTime;
    Date date;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        updateDisplay();
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String id = SharedPref.instance.getIDMember();
        Member m = DbContext.instance.findMemberWithId(id);
        date = DbContext.instance.getDateStart();
        Log.e(TAG, String.format("onStart: %s", m == null));
        if (m != null) {
            tvTimeStart.setText("Thời gian bắt đầu: " + Utils.getTime(date));
            Log.e(TAG, String.format("onCreate: %s", m.toString()));
            tvName.setText("Tên nhân viên: " + m.getName());
            tvTime.setText("Thời gian làm việc: " + Utils.getTime(date, new Date()) + " phút");
        }
    }

    private void addListener() {
        btAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAction.startAnimation(buttonClick);
                Intent intent = new Intent(MainActivity.this, ActionActivity.class);
                startActivity(intent);
            }
        });
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btMain.startAnimation(buttonClick);
            }
        });
    }

    private void updateDisplay() {
        Timer timer = new Timer();
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

        }, 0, 60000);//Update text every second
    }

    public void onTimeChange(TimeChangeEvent event) {
        tvTime.setText(event.getTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            String id = Utils.byteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Log.e("UID", String.format("onNewIntent: %s", id));
            WC wc = DbContext.instance.findWCWithId(id);
            if (wc == null) {
                Toast.makeText(MainActivity.this, "Khu vực chưa được đăng ký", Toast.LENGTH_SHORT).show();
                return;
            }
            if (SharedPref.instance.getCheckId() != null && id.equalsIgnoreCase(SharedPref.instance.getCheckId())) {
                Toast.makeText(MainActivity.this, "Thoát khỏi " + wc.getName(), Toast.LENGTH_SHORT).show();

            } else if (SharedPref.instance.getCheckId() == null) {
                Toast.makeText(this, "Kiểm tra " + wc.getName(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, CheckListActivity.class);
                startActivity(intent1);
                finish();
            } else if (SharedPref.instance.getCheckId() != null && !id.equalsIgnoreCase(SharedPref.instance.getCheckId())) {
                Toast.makeText(this, "Bạn đang ở " + wc.getName() + " bạn không thể vào khu vực khác!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void setNameTextView() {
        String s = "Thời gian bắt đầu làm việc: ";
        Time dtNow = new Time();
        dtNow.setToNow();
        int hours = dtNow.hour;
        String lsNow = s + dtNow.format("%Y.%m.%d %H:%M:%S");
        Member member = DbContext.instance.findMemberWithId(SharedPref.instance.getIDMember());


    }
}
