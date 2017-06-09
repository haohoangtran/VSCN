package lachongmedia.vn.nfc.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.adapters.CheckListAdapter;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.models.Member;
import lachongmedia.vn.nfc.database.models.PlanWork;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.realm.realm_models.DiaDiemSave;
import lachongmedia.vn.nfc.database.realm.realm_models.RealmString;
import lachongmedia.vn.nfc.database.respon.login.Dschecklist;
import lachongmedia.vn.nfc.eventbus_event.CameraEvent;
import lachongmedia.vn.nfc.eventbus_event.TimeChangeEvent;
import vn.lachongmedia.ksmartg.chupanhlibrary.activities.ChupAnhActivity;

public class CheckListActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 124;
    private static final String TAG = CheckListActivity.class.toString();
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
    @BindView(R.id.rv_check_list)
    RecyclerView rvCheckList;
    @BindView(R.id.bt_back_tut)
    Button bt_back;
    @BindView(R.id.tv_vitri)
    TextView tvVitri;
    @BindView(R.id.tv_timemax)
    TextView tvTimeMax;
    @BindView(R.id.tv_vitritieptheo)
    TextView tvVitriTieptheo;
    Date date;
    CheckListAdapter adapter;
    DiaDiemSave diaDiemSave;
    private Dschecklist dschecklistCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Hạng mục cần kiểm tra");

        }
        adapter = new CheckListAdapter();
        rvCheckList.setAdapter(adapter);
        rvCheckList.setLayoutManager(new LinearLayoutManager(this));
        addListenner();
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
        EventBus.getDefault().register(this);
        if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
            diaDiemSave = RealmDatabase.instance.getDiaDiemSave().get(0);
        }
        PlanWork planWork = DbContext.instance.getPlaceWorkNext();
        tvVitri.setText("Tên địa điểm: " + diaDiemSave.getDsdiadiem().getTendiadiem());
        date = RealmDatabase.instance.getDateStringStartFromRealm(SharedPref.instance.getIDUser());
        if (planWork != null)
            tvVitriTieptheo.setText("Vị trí tiếp theo: " + planWork.getDsdiadiem().getTendiadiem());
        else
            tvVitriTieptheo.setText("Vị trí tiếp theo: Không khả dụng! ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
                        long minute = Utils.getTime(date, new Date());
                        if (minute < 60)
                            onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + minute + " phút"));
                        else {
                            onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + (int) minute / 60 + " giờ " + minute % 60 + " phút"));
                        }
                    }
                });

            }

        }, 0, 60000);//Update text every 60 seconds
    }

    public void onTimeChange(TimeChangeEvent event) {
        long minute = Utils.getTime(Utils.stringToDate(diaDiemSave.getTime()), new Date());
        tvTimeMax.setText("Thời gian tại điểm: " + minute + "/" + diaDiemSave.getDsdiadiem().getThoigiantoida() + " phút tối đa");
    }

    @Subscribe
    public void onCameraEvent(CameraEvent event) {
        this.dschecklistCapture = event.getDschecklist();
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage("Bạn có muốn chụp ảnh cho bước kiểm tra ")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChupAnhActivity.pathsList = new Vector<>();
                        Intent cameraIntent = new Intent(CheckListActivity.this, ChupAnhActivity.class);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                })
                .setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void addListenner() {
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckListActivity.this, TutorialActivity.class);
                intent.putExtra("type", "dung");
                startActivity(intent);
            }
        });
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
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            String id = Utils.byteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Log.e("UID", String.format("onNewIntent: %s", id));
            if (RealmDatabase.instance.getDiaDiemSave().size() != 0)
                if (id.equals(RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getIdThediadiem())) {
                    Toast.makeText(this, "Báo cáo thành công", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    RealmDatabase.instance.removePlaceSave();
                } else {
                    Toast.makeText(this, "Báo cáo lỗi,thẻ k hợp lệ", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (dschecklistCapture != null) {
                RealmDatabase.instance.setPathsImage(dschecklistCapture, ChupAnhActivity.pathsList);
            }
        }
    }
}
