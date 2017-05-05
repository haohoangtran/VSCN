package lachongmedia.vn.nfc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.AlphaAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;

public class ActionActivity extends AppCompatActivity {
    @BindView(R.id.bt_main)
    AppCompatButton btMain;
    @BindView(R.id.bt_action)
    AppCompatButton btAction;
    @BindView(R.id.bt_logout)
    AppCompatButton btLogout;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        addListener();
    }

    private void addListener() {
        btAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAction.startAnimation(buttonClick);
            }
        });
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btMain.startAnimation(buttonClick);
                Intent intent = new Intent(ActionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.instance.logout();
                Intent intent = new Intent(ActionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
