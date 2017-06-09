package lachongmedia.vn.nfc.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.realm.realm_models.DiaDiemSave;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.Dshuongdan;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import pl.droidsonroids.gif.GifImageView;

public class TutorialActivity extends AppCompatActivity {
    private static final String TAG = TutorialActivity.class.getSimpleName();
    @BindView(R.id.intro_viewpager)
    ViewPager viewPager;
    MyViewPagerAdapter myViewPagerAdapter;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    TextView[] dots;
    @BindView(R.id.bt_skip)
    FButton btSkip;
    @BindView(R.id.bt_next)
    FButton btNext;
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.tv_plant_work_list)
    TextView tvPlantWorklist;
    @BindView(R.id.tv_location_next)
    TextView tvLocationNext;
    @BindView(R.id.tv_time_work)
    TextView tvTimeWork;
    @BindView(R.id.tv_work_now)
    TextView tvWorkNow;
    @BindView(R.id.iv_time_work)
    ImageView ivTimeWork;
    private int[] layouts;
    LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        private final String TAG = TutorialActivity.class.getSimpleName();

        @Override
        public void onPageSelected(int position) {
            addBottomDot(position);

            tvStep.setText("Bước " + (position + 1) + "/" + dots.length);
            if (position == layouts.length - 1) {
                btSkip.setVisibility(View.GONE);
            } else {
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
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //Roboto-Thin.ttf
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        btNext.setTypeface(typeface);
        btSkip.setTypeface(typeface);
        layouts = new int[DbContext.instance.getDshuongdanList().size()];
        for (int i = 0; i < layouts.length; i++) {
            layouts[i] = R.layout.intro_slide_tutorial;
        }

        addBottomDot(0);
        Log.e(TAG, String.format("onCreate: %s", DbContext.instance.getDshuongdanList()));
        changeSttBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
                    type = getIntent().getStringExtra("type");
                    if (type.equalsIgnoreCase("dung")) {
                        Intent intent = new Intent(TutorialActivity.this, CheckListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TutorialActivity.this, "Bạn chưa vào địa điểm này!", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(TutorialActivity.this, "Bạn chưa vào điểm này, không thể báo cáo", Toast.LENGTH_SHORT).show();
            }
        });
        updateDisplay();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                finish();
            }
        }, intentFilter);
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

    DiaDiemSave diaDiemSave;

    private void updateDisplay() {
        Timer timer = new Timer();
        if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
            diaDiemSave = RealmDatabase.instance.getDiaDiemSave().get(0);
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        type = getIntent().getStringExtra("type");
                        Log.e(TAG, String.format("run: %s", type));
                        if (type != null && type.equalsIgnoreCase("dung")) {
                            long minute = Utils.getTime(Utils.stringToDate(diaDiemSave.getTime()), new Date());

                            ivTimeWork.setVisibility(View.VISIBLE);
                            tvTimeWork.setVisibility(View.VISIBLE);
                            tvTimeWork.setText(minute + "/" + diaDiemSave.getDsdiadiem().getThoigiantoida());
                        } else {
                            ivTimeWork.setVisibility(View.GONE);
                            tvTimeWork.setVisibility(View.GONE);
                        }
                        tvLocationNext.setText(DbContext.instance.getPlaceWorkNext().getDsdiadiem().getTendiadiem());
                        tvPlantWorklist.setText("0/" + DbContext.instance.getPlanWorkList().size());
                    }
                });

            }

        }, 0, 60000);//Update text every 60 seconds
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void changeSttBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        type = getIntent().getStringExtra("type");
        if (diaDiemSave != null) {
            tvWorkNow.setText(diaDiemSave.getDsdiadiem().getTendiadiem());
        } else {
            Log.e(TAG, String.format("onCreate: %s", getIntent().getStringExtra("name")));
            tvWorkNow.setText(getIntent().getStringExtra("name") != null ? getIntent().getStringExtra("name") : "");
        }
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            Dshuongdan ds = DbContext.instance.getDshuongdanList().get(position);
            final ScrollView scrollView = (ScrollView) view.findViewById(R.id.sv_tut);
            TextView tvTren = (TextView) view.findViewById(R.id.intro_title);
            final GifImageView ivGif = (GifImageView) view.findViewById(R.id.iv_down_arrow);
            TextView tvDuoi = (TextView) view.findViewById(R.id.intro_content);
            ImageView ivTutorial = (ImageView) view.findViewById(R.id.intro_logo);
            Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
            Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
            tvTren.setTypeface(typeface1);
            tvDuoi.setTypeface(typeface2);
            tvTren.setText(ds.getNoidung());
            tvDuoi.setText(ds.getYeucau());
            Picasso.with(view.getContext()).load(ds.getPath()).into(ivTutorial);
            if (Utils.canScroll(scrollView)) {
                ivGif.setVisibility(View.INVISIBLE);
            } else {
                ivGif.setVisibility(View.VISIBLE);
            }
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (scrollView != null) {
                        if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            ivGif.setVisibility(View.INVISIBLE);
                        } else {
                            //scroll view is not at bottom
                            ivGif.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
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
