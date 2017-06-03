package vn.lachongmedia.ksmartg.chupanhlibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.lachongmedia.ksmartg.chupanhlibrary.R;


public class ConfigFlashActivity extends AppCompatActivity {
    private TextView bt_tudong, bt_bat, bt_tat;
    LinearLayout ll_tudong, ll_On, ll_tat;
    Button bt_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_flash);
        initView();
        bt_bat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("batFlash", "Bật");
                setResult(1, intent);
                finish();
            }
        });
        bt_tat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tatFlash", "Tắt");
                setResult(2, intent);
                finish();
            }
        });
        bt_tudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tudongFlash", "Tự động");
                setResult(3, intent);
                finish();
            }
        });
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_tudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_tudong.performClick();
            }
        });

        ll_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_bat.performClick();
            }
        });

        ll_tat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_tat.performClick();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(6, intent);
        finish();
    }

    public void initView() {
        bt_tudong = (TextView) findViewById(R.id.bt_tudong);
        bt_bat = (TextView) findViewById(R.id.bt_bat);
        bt_tat = (TextView) findViewById(R.id.bt_tat);
        ll_tudong = (LinearLayout) findViewById(R.id.ll_tudong);
        ll_On = (LinearLayout) findViewById(R.id.ll_On);
        ll_tat = (LinearLayout) findViewById(R.id.ll_tat);
        bt_close = (Button) findViewById(R.id.btnClose);
    }
}
