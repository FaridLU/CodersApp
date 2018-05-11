package com.example.farid.codersappdemo;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;

public class user_profile extends AppCompatActivity
    implements AppBarLayout.OnOffsetChangedListener{

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    ViewPager viewPager1, viewPager2, viewPager3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        bindActivity();

        mAppBarLayout.addOnOffsetChangedListener(this);
        //mToolbar.inflateMenu(R.menu.profile_menu);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        // This is for viewpager
        viewPager1 = findViewById(R.id.view_pager1);
        SpringIndicator springIndicator1 = findViewById(R.id.indicator1);

        viewPager2 = findViewById(R.id.view_pager2);
        SpringIndicator springIndicator2 = findViewById(R.id.indicator2);

        viewPager3 = findViewById(R.id.view_pager3);
        SpringIndicator springIndicator3 = findViewById(R.id.indicator3);

        PagerModelManager manager1 = new PagerModelManager();
        manager1.addCommonFragment(viewpager_guide.class,  getBgRes(), getTitles());
        PagerModelManager manager2 = new PagerModelManager();
        manager2.addCommonFragment(viewpager_guide.class,  getBgRes(), getTitles());
        PagerModelManager manager3 = new PagerModelManager();
        manager3.addCommonFragment(viewpager_guide.class,  getBgRes(), getTitles());

        ModelPagerAdapter adapter1 = new ModelPagerAdapter(getSupportFragmentManager(), manager1);
        viewPager1.setAdapter(adapter1);
        ModelPagerAdapter adapter2 = new ModelPagerAdapter(getSupportFragmentManager(), manager2);
        viewPager2.setAdapter(adapter2);
        ModelPagerAdapter adapter3 = new ModelPagerAdapter(getSupportFragmentManager(), manager3);
        viewPager3.setAdapter(adapter3);

        //viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator1.setViewPager(viewPager1);
        springIndicator2.setViewPager(viewPager2);
        springIndicator3.setViewPager(viewPager3);

    }

    public class ViewPagerAttr implements Serializable {
        public int vp_img;
        public String vp_title, vp_ans;
        ViewPagerAttr(int vp_img, String vp_title, String vp_ans) {
            this.vp_img = vp_img;
            this.vp_title = vp_title;
            this.vp_ans = vp_ans;
        }
    }

    private List<String> getTitles(){
        List<String> list = new ArrayList<>();
        list.add("1"); list.add("2"); list.add("3"); list.add("4");
        return list;
    }

    private List<ViewPagerAttr> getBgRes(){
        List<ViewPagerAttr> list = new ArrayList<>();
        list.add(new ViewPagerAttr(R.drawable.ranking, "Rating", "1745"));
        list.add(new ViewPagerAttr(R.drawable.man, "Total Problem Solved", "1745"));
        list.add(new ViewPagerAttr(R.drawable.browse, "demo1", "1745"));
        list.add(new ViewPagerAttr(R.drawable.calculator, "demo 2", "1745"));
        return list;
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