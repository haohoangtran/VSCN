package lachongmedia.vn.nfc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.database.DbContext;

public class ActionActivity extends AppCompatActivity {
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
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
    }

    private void addListener() {

        llMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llMap.startAnimation(buttonClick);
                Intent intent = new Intent(ActionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.instance.logout();
            }
        });
    }
}
