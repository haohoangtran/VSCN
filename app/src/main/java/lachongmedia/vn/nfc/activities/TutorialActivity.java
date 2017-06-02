package lachongmedia.vn.nfc.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;

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
    @BindView(R.id.tv_time_content)
    TextView tvTimeTop;
    private int[] layouts;
    LoginRespon loginRespon=RealmDatabase.instance.getLoginRespon();
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        private final String TAG = TutorialActivity.class.getSimpleName();

        @Override
        public void onPageSelected(int position) {
            addBottomDot(position);

            tvStep.setText("Bước " + (position + 1) + "/" + dots.length);
            if (position == layouts.length - 1) {
                btNext.setText("Đã hiểu");
                btSkip.setVisibility(View.GONE);
            } else {
                btNext.setText("Báo cáo công việc");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        }
        ButterKnife.bind(this);
        //Roboto-Thin.ttf
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        btNext.setTypeface(typeface);
        btSkip.setTypeface(typeface);


        layouts = new int[DbContext.instance.getDshuongdanList().size()];
        for (int i = 0; i < layouts.length; i++) {
            layouts[i] = R.layout.intro_slide_tutorial;
        }
        addBottomDot(0);
        Log.e(TAG, String.format("onCreate: %s", DbContext.instance.getDshuongdanList()));

        changeSttBarColor();
        StringBuilder builder=new StringBuilder();
        builder.append("Địa điểm: 0/").append(loginRespon.getKehoach().getDsdiadiem().size())
                .append("\t ĐỊa điểm kế tiếp: " + loginRespon.getKehoach());
        ;
        tvTimeTop.setText(builder.toString());

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
                if (RealmDatabase.instance.getDiaDiemSave().size()!=0) {
                    Intent intent = new Intent(TutorialActivity.this, CheckListActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(TutorialActivity.this, "Bạn chưa vào điểm này, không thể báo cáo", Toast.LENGTH_SHORT).show();
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

    private void changeSttBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
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
            TextView tvTren = (TextView) view.findViewById(R.id.intro_title);
            TextView tvDuoi = (TextView) view.findViewById(R.id.intro_content);
            ImageView ivTutorial = (ImageView) view.findViewById(R.id.intro_logo);
            Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
            Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
            tvTren.setTypeface(typeface1);
            tvDuoi.setTypeface(typeface2);
            tvTren.setText(ds.getNoidung());
            tvDuoi.setText(ds.getYeucau());
            Picasso.with(view.getContext()).load(ds.getPath()).into(ivTutorial);
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
