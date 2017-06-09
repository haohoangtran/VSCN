package lachongmedia.vn.nfc.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.adapters.PlanAdapter;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;

public class ActionActivity extends AppCompatActivity {
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_work)
    LinearLayout llKehoach;
    @BindView(R.id.ll_report_issue)
    LinearLayout llReportIssue;
    @BindView(R.id.ll_ckecklist_sent)
    LinearLayout llCheckListSent;
    LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.bind(this);
        buttonClick.setFillAfter(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tác vụ");
        }
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

    private void addListener() {

        llMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llMap.startAnimation(buttonClick);
                Intent intent = new Intent(ActionActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.instance.logout();
                RealmDatabase.instance.removeAllData();
                DbContext.instance.reset();
                Intent intent = new Intent(ActionActivity.this.getApplicationContext(), LoginActivity.class);
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                sendBroadcast(broadcastIntent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ActionActivity.this.startActivity(intent);
            }
        });
        llKehoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ActionActivity.this);
                dialog.setContentView(R.layout.plan_dialog);
                dialog.setTitle("Kế hoạch trong ngày");
                RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.rv_plans);
                AppCompatButton bt = (AppCompatButton) dialog.findViewById(R.id.bt_ok);
                PlanAdapter adapter = new PlanAdapter();
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(ActionActivity.this));
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        llReportIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActionActivity.this, ReportIssueActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
