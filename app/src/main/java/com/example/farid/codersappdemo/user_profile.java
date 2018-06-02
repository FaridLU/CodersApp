package com.example.farid.codersappdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;

public class user_profile extends AppCompatActivity
    implements AppBarLayout.OnOffsetChangedListener, Serializable{

    public static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    public static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    public static final int ALPHA_ANIMATIONS_DURATION              = 200;

    public boolean mIsTheTitleVisible          = false;
    public boolean mIsTheTitleContainerVisible = true;

    public LinearLayout mTitleContainer;
    public TextView mTitle;
    public AppBarLayout mAppBarLayout;
    public Toolbar mToolbar;

    public ViewPager viewPager1, viewPager2, viewPager3, viewPager4;
    public String cf_handle, cc_handle, name, uva_handle;
    public TextView profile_title, username;

    user_profile_activity userActivity = new user_profile_activity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        bindActivity();
        mAppBarLayout.addOnOffsetChangedListener(this);
        //mToolbar.inflateMenu(R.menu.profile_menu);

        userActivity = (user_profile_activity) getIntent().getSerializableExtra("list");

        name = getIntent().getStringExtra("name");
        addInformationOnProfile();
        makeViewPager();

    }



    void makeViewPager() {
        // This is for viewpager
        viewPager1 = findViewById(R.id.view_pager1);
        SpringIndicator springIndicator1 = findViewById(R.id.indicator1);

        viewPager2 = findViewById(R.id.view_pager2);
        SpringIndicator springIndicator2 = findViewById(R.id.indicator2);

        viewPager3 = findViewById(R.id.view_pager3);
        SpringIndicator springIndicator3 = findViewById(R.id.indicator3);

        viewPager4 = findViewById(R.id.view_pager4);
        SpringIndicator springIndicator4 = findViewById(R.id.indicator4);

        PagerModelManager manager1 = new PagerModelManager();
        manager1.addCommonFragment(viewpager_guide.class,  getBgRes(1), getTitles());
        PagerModelManager manager2 = new PagerModelManager();
        manager2.addCommonFragment(viewpager_guide.class,  getBgRes(2), getTitles());
        PagerModelManager manager3 = new PagerModelManager();
        manager3.addCommonFragment(viewpager_guide.class,  getBgRes(3), getTitles());
        PagerModelManager manager4 = new PagerModelManager();
        manager4.addCommonFragment(viewpager_guide.class,  getBgRes(4), getTitles());

        ModelPagerAdapter adapter1 = new ModelPagerAdapter(getSupportFragmentManager(), manager1);
        viewPager1.setAdapter(adapter1);
        ModelPagerAdapter adapter2 = new ModelPagerAdapter(getSupportFragmentManager(), manager2);
        viewPager2.setAdapter(adapter2);
        ModelPagerAdapter adapter3 = new ModelPagerAdapter(getSupportFragmentManager(), manager3);
        viewPager3.setAdapter(adapter3);
        ModelPagerAdapter adapter4 = new ModelPagerAdapter(getSupportFragmentManager(), manager4);
        viewPager4.setAdapter(adapter4);


        // just set viewPager
        springIndicator1.setViewPager(viewPager1);
        springIndicator2.setViewPager(viewPager2);
        springIndicator3.setViewPager(viewPager3);
        springIndicator4.setViewPager(viewPager4);
    }

    public void addInformationOnProfile() {
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        username = findViewById(R.id.profile_username);
        profile_title = findViewById(R.id.profile_textview_title);

        username.setText(name);
        profile_title.setText(name);
    }

    private List<String> getTitles (){
        List<String> list = new ArrayList<>();
        list.add("1"); list.add("2"); list.add("3"); list.add("4");
        return list;
    }

    private List<ViewPagerAttr> getBgRes(int type){
        List<ViewPagerAttr> list = new ArrayList<>();

        if(type == 1 ) {
            list.add(new ViewPagerAttr(R.drawable.ranking, "Rating", userActivity.cf_rating, 1, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.count, "Total Submissions", userActivity.cf_totalSubmissions, 1, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.programmer, "Type of Coder", userActivity.cf_typeOfCoder, 1, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.rank, "Max Rank", userActivity.cf_maxRank, 1, 1, null, null, null));
        } else if(type == 2) {
            list.add(new ViewPagerAttr(R.drawable.ranking, "Rating", userActivity.cc_rating, 2, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.count, "Total Problem Solved", userActivity.cc_totalSolved, 2, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.programmer, "Type of Coder", userActivity.cc_typeOfCoder, 2, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.rank, "Country Rank", userActivity.cc_countryRank, 2, 1, null, null, null));
        } else if(type == 3) {
            list.add(new ViewPagerAttr(R.drawable.ranking, "Rating", "Not Added Yet", 3, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.count, "Total Problem Solved", "Not Added Yet", 3, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.programmer, "Type of Coder", "Not Added Yet", 3, 1, null, null, null));
            list.add(new ViewPagerAttr(R.drawable.rank, "Global Rank", "Not Added Yet", 3, 1, null, null, null));
        } else if(type == 4) {
            list.add(new ViewPagerAttr(R.drawable.combined, "Recent Activities", null, 4, 1, cf_handle, cc_handle, uva_handle));
            list.add(new ViewPagerAttr(R.drawable.codeforces_full, "Codeforces Activities", null, 4, 2, cf_handle, cc_handle, uva_handle));
            list.add(new ViewPagerAttr(R.drawable.codechef_full, "Codechef Activities", null, 4, 3, cf_handle, cc_handle, uva_handle));
            list.add(new ViewPagerAttr(R.drawable.uva, "UVA Activities", null, 4, 4, cf_handle, cc_handle, uva_handle));
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void bindActivity() {
        mToolbar        = findViewById(R.id.profile_toolbar);
        mTitle          = findViewById(R.id.profile_textview_title);
        mTitleContainer = findViewById(R.id.profile_linearlayout_title);
        mAppBarLayout   = findViewById(R.id.profile_appbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
class ViewPagerAttr implements Serializable {
    public transient int vp_img, vp_type, vp_index;
    public transient String vp_title, vp_ans, vp_cf_handle, vp_cc_handle, vp_uva_handle;
    ViewPagerAttr(int vp_img, String vp_title, String vp_ans, int vp_type, int vp_index, String vp_cf_handle, String vp_cc_handle, String vp_uva_handle) {
        this.vp_img = vp_img;
        this.vp_title = vp_title;
        this.vp_ans = vp_ans;
        this.vp_type = vp_type;
        this.vp_index = vp_index;
        this.vp_cf_handle = vp_cf_handle;
        this.vp_cc_handle = vp_cc_handle;
        this.vp_uva_handle = vp_uva_handle;
    }
}