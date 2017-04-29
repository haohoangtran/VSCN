package lachongmedia.vn.nfc.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.SharedPref;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;

public class TutorialActivity extends AppCompatActivity {
    @BindView(R.id.intro_viewpager)
    ViewPager viewPager;
    MyViewPagerAdapter myViewPagerAdapter;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    TextView[] dots;
    private int[] layouts;
    @BindView(R.id.bt_skip)
    Button btSkip;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.tv_step)
    TextView tvStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            //api 21 méo dc
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_tutorial);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        //Roboto-Thin.ttf
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        btNext.setTypeface(typeface);
        btSkip.setTypeface(typeface);
        tvStep.setText("Bước 1/8");

        layouts = new int[]{
                R.layout.intro_slide1,
                R.layout.intro_slide2,
                R.layout.intro_slide3,
                R.layout.intro_slide4,
                R.layout.intro_slide5,
                R.layout.intro_slide6,
                R.layout.intro_slide7,
                R.layout.intro_slide8
        };

        addBottomDot(0);

        changeSttBarColo();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TutorialActivity.this,CheckListActivity.class);
                startActivity(intent);
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    startActivity(new Intent(TutorialActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    TutorialActivity.this.finish();
                }
            }
        });
    }

    private void addBottomDot(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);

        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        private final String TAG = TutorialActivity.class.getSimpleName();

        @Override
        public void onPageSelected(int position) {
            addBottomDot(position);

            tvStep.setText("Bước "+(position+1)+"/"+dots.length);
            if (position == layouts.length - 1) {
                btNext.setText("Đã hiểu");
                btSkip.setVisibility(View.GONE);
            } else {
                btNext.setText("Tiếp theo");
                btSkip.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            Log.e(TAG, String.format("onPageScrolled: %s %s %s", arg0, arg1, arg2));

        }


        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeSttBarColo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            TextView tv1 = (TextView) view.findViewById(R.id.intro1_title);
            TextView tv2 = (TextView) view.findViewById(R.id.intro1_content);
            Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
            Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
            tv1.setTypeface(typeface1);
            tv2.setTypeface(typeface2);

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
}
