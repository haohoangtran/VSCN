package vn.lachongmedia.ksmartg.chupanhlibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.lachongmedia.ksmartg.chupanhlibrary.R;


public class ConfigQualityActivity extends AppCompatActivity {
    TextView tvMacdinh;
    TextView tvCao;
    LinearLayout ll_macdinh, ll_cao;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_quality);
        initView();
        tvCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("qualityCao", "Cao");
                setResult(4, intent);
                finish();
            }
        });
        tvMacdinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("qualityMacdinh", "Mặc định");
                setResult(5, intent);
                finish();
            }
        });
        ll_cao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCao.performClick();
            }
        });
        ll_macdinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMacdinh.performClick();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(7, intent);
        finish();
    }

    private void initView() {
        tvMacdinh = (TextView) findViewById(R.id.bt_macdinh);
        tvCao = (TextView) findViewById(R.id.bt_cao);
        ll_cao = (LinearLayout) findViewById(R.id.ll_cao);
        ll_macdinh = (LinearLayout) findViewById(R.id.ll_macdinh);
        btnClose = (Button) findViewById(R.id.btnClose);
    }
}
