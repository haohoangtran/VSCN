package lachongmedia.vn.nfc.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.realm.realm_models.DateString;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import lachongmedia.vn.nfc.eventbus_event.LoginCompleteEvent;
import lachongmedia.vn.nfc.networks.NetContext;
import lachongmedia.vn.nfc.server.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    // list of NFC technologies detected:
    public static Date date;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }


    @Override
    protected void onStart() {
        super.onStart();
        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (SharedPref.instance.getIDTag() != null) {
            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
            finish();
        }
        if (!(adapter != null && adapter.isEnabled())) {
            AlertDialog dialog = new AlertDialog.Builder(this).setMessage("Bạn chưa bật NFC ấn ok để vào menu").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_NFC_SETTINGS), 0);
                }
            }).create();
            dialog.setCancelable(false);
            dialog.show();
        }
        if (adapter != null && !adapter.isEnabled()) {
//            tvName.setText("Bạn phải bật NFC để sử dụng!");
        } else {
//            tvName.setText("Bạn chưa vào phiên làm việc! Quẹt thẻ để tiếp tục");
        }

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
            final String id = Utils.byteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Log.e("higu", String.format("onResponse: hihihi %s", id));
            LoginService loginService = NetContext.instance.create(LoginService.class);
            loginService.login(id).enqueue(new Callback<LoginRespon>() {
                @Override
                public void onResponse(Call<LoginRespon> call, Response<LoginRespon> response) {
                    if (response.code() == 200) {
                        final LoginRespon respon = response.body();
                        if (respon != null) {
                            RealmDatabase.instance.insertOrUpdateLogin(respon);
                            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Date da = new Date();
                            RealmDatabase.instance.addToRealmDateString(new DateString(da, respon.getNhanvien().getIdNhanvien(), 0));
                            SharedPref.instance.putIDUser(respon.getNhanvien().getIdNhanvien());
                            SharedPref.instance.putIdTag(id);
                            EventBus.getDefault().postSticky(new LoginCompleteEvent());
                            startActivity(intent1);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Thẻ của bạn chưa được đăng ký!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginRespon> call, Throwable t) {
                    Log.e("hihu", String.format("onFailure: %s", t.toString()));
                }
            });
        }
    }

}
