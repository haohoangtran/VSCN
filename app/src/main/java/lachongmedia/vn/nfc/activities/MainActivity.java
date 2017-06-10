package lachongmedia.vn.nfc.activities;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.realm_models.PlanWork;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.realm.realm_models.DiaDiemSave;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.Dsmatbang;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import lachongmedia.vn.nfc.eventbus_event.LoginCompleteEvent;
import lachongmedia.vn.nfc.eventbus_event.PlanworkEvent;
import lachongmedia.vn.nfc.eventbus_event.TimeChangeEvent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    final LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
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
    @BindView(R.id.ll_work_now)
    LinearLayout llWork;
    @BindView(R.id.tv_work_next)
    TextView tvWorkNext;
    @BindView(R.id.tv_name_nvs)
    TextView tvNameNvs;
    @BindView(R.id.vp_matbang)
    ViewPager vpMatBang;
    MyViewPagerAdapter adapter;
    List<String> paths;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (DbContext.instance.getPlanWorkList().size() == 0)
            DbContext.instance.createPlanWorks(loginRespon);
        paths = new Vector<>();
        ButterKnife.bind(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        updateDisplay();
        addListener();
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
        date = RealmDatabase.instance.getDateStringStartFromRealm(SharedPref.instance.getIDUser());

        EventBus.getDefault().register(this);
        LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
        if (loginRespon != null) {
            tvTimeStart.setText("Thời gian bắt đầu: " + Utils.getTime(date));
            tvName.setText("Tên nhân viên: " + loginRespon.getNhanvien().getTennhanvien());
            tvTime.setText("Thời gian làm việc: " + Utils.getTime(date, new Date()) + " phút");
        } else
            llWork.setVisibility(View.GONE);
        llWork.setVisibility(View.GONE);
        if (DbContext.instance.getPlaceWorkNext() != null)
            tvWorkNext.setText("Địa điểm tiếp theo: " + DbContext.instance.getPlaceWorkNext().getDsdiadiem().getTendiadiem());
        else
            tvWorkNext.setText("Địa điểm tiếp theo: Không khả dụng");
        paths = DbContext.instance.getPaths();
        Log.e(TAG, String.format("onEvent: %s", paths));
        layouts = new int[paths.size()];
        for (int i = 0; i < layouts.length; i++) {
            layouts[i] = R.layout.layout_matbang;
        }
        adapter = new MyViewPagerAdapter();
        vpMatBang.setAdapter(adapter);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFirstLogin(LoginCompleteEvent event) {
        EventBus.getDefault().removeStickyEvent(LoginCompleteEvent.class);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.person_dialog);
        ImageView ivAvt = (ImageView) dialog.findViewById(R.id.iv_avt);
        AppCompatButton btOk = (AppCompatButton) dialog.findViewById(R.id.bt_ok);
        TextView tvName = (TextView) dialog.findViewById(R.id.tv_name);
        TextView tvType = (TextView) dialog.findViewById(R.id.tv_type);
        TextView tvTime = (TextView) dialog.findViewById(R.id.tv_time);
        tvTime.setText("Thời gian bắt đầu: " + Utils.getTime(date));
        tvType.setText("Vị trí: " + loginRespon.getNhanvien().getTenloainhanvien());
        Picasso.with(this).load(loginRespon.getNhanvien().getPath()).into(ivAvt);
        tvName.setText("Tên: " + loginRespon.getNhanvien().getTennhanvien());
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setTitle("Thông tin đăng nhập");
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        llWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckListActivity.class);
                startActivity(intent);
                finish();
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
                        try {
                            long minute = Utils.getTime(date, new Date());
                            if (minute < 60)
                                onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + minute + " phút"));
                            else {
                                onTimeChange(new TimeChangeEvent("Thời gian làm việc: " + (int) minute / 60 + " giờ " + minute % 60 + " phút"));
                            }

                            if (DbContext.instance.getPlaceWorkNext() != null)
                                tvWorkNext.setText("Địa điểm tiếp theo: " + DbContext.instance.getPlaceWorkNext().getDsdiadiem().getTendiadiem());
                            else
                                tvWorkNext.setText("Địa điểm tiếp theo: Không khả dụng");
                        } catch (Exception e) {
                            Log.e(TAG, String.format("run: %s", e.toString()));
                        }
                    }
                });

            }

        }, 0, 60000);//Update text every 60 seconds
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
            if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
                Toast.makeText(this, String.format("Bạn  đang thực hiện ở: %s", RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getTendiadiem()), Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().size(); i++) {
                Dsdiadiem dsdiadiem = loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().get(i);
                if (dsdiadiem.getIdThediadiem().equalsIgnoreCase(id)) {
                    if (DbContext.instance.getPlanWorkWithDate(new Date(), dsdiadiem) == null) {
                        Toast.makeText(this, "Chưa đến giờ làm việc tại điểm này", Toast.LENGTH_SHORT).show();
                    } else if (DbContext.instance.getPlanWorkWithDate(new Date(), dsdiadiem).isCompleted() == 0) {
                        DiaDiemSave diaDiemSave = new DiaDiemSave(dsdiadiem, loginRespon.getNhanvien());
                        RealmDatabase.instance.saveDiaDiemSave(diaDiemSave);
                        DbContext.instance.setDshuongdanList(diaDiemSave.getDsdiadiem().getDshuongdan());
                        DbContext.instance.setDateJoinPlace(new Date());
                        Intent intent1 = new Intent(MainActivity.this, TutorialActivity.class);
                        intent1.putExtra("name", diaDiemSave.getDsdiadiem().getTendiadiem());
                        intent1.putExtra("type", "dung");
                        startActivity(intent1);
                        PlanWork planWork = DbContext.instance.getPlanWorkWithDate(new Date(), dsdiadiem);
                        EventBus.getDefault().postSticky(new PlanworkEvent(planWork));
                        planWork.setCompleted(2);
                        Log.d(TAG, String.format("onNewIntent: %s", planWork.toString()));
                    } else if (DbContext.instance.getPlanWorkWithDate(new Date(), dsdiadiem).isCompleted() == 1) {

                        Toast.makeText(this, "Bạn đã hoàn thành công việc", Toast.LENGTH_SHORT).show();
                    } else if (DbContext.instance.getPlanWorkWithDate(new Date(), dsdiadiem).isCompleted() == -1) {
                        Toast.makeText(this, "Bạn đã quá thời gian làm việc", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    private class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            ImageView img = (ImageView) view.findViewById(R.id.iv_home);
            TextView tvStep = (TextView) view.findViewById(R.id.tv_step);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
            tvStep.setTypeface(typeface);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position < RealmDatabase.instance.getLoginRespon().getKehoach().getSite().getDsmatbang().size()) {
                        Dsmatbang dsmatbang = RealmDatabase.instance.getLoginRespon().getKehoach().getSite().getDsmatbang().get(position);
                        Log.e(TAG, String.format("onClick: %s", dsmatbang));
                        Log.e(TAG, String.format("onClick: %s", dsmatbang.getTenmatbang()));
                        Intent intent = new Intent(MainActivity.this, GroundActivity.class);
                        DbContext.instance.setListDiadiemMatBang(dsmatbang.getDsdiadiem());
                        intent.putExtra("name", dsmatbang.getTenmatbang());
                        startActivity(intent);
                    }
                }
            });
            tvStep.setText("Mặt bằng: " + (position + 1) + "/" + layouts.length);
            Picasso.with(MainActivity.this).load(paths.get(position)).into(img);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
