package com.example.farid.codersappdemo.contest;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.farid.codersappdemo.MainActivity;
import com.example.farid.codersappdemo.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.stone.vega.library.VegaLayoutManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class main_contest extends AppCompatActivity {

    private List<ContestActivity> Codeforces_list = new ArrayList<>();
    private List<ContestActivity> CodeChef_list = new ArrayList<>();
    private List<ContestActivity> All_Contest_list = new ArrayList<>();

    CoordinatorLayout main_contest;
    RelativeLayout activity_loading;


    public LinearLayout faceook, whatsapp, telegram, email, googleplus, messenger, bottom_she, share_bottom_sheet;
    public BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;
    private Context mContext = this;
    private AnimatedCircleLoadingView animatedCircleLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contest);

        faceook = findViewById(R.id.facebook_share);
        whatsapp = findViewById(R.id.whatsapp_share);
        telegram = findViewById(R.id.telegram_share);
        googleplus = findViewById(R.id.googleplus_share);
        messenger = findViewById(R.id.messenger_share);
        email = findViewById(R.id.email_share);
        share_bottom_sheet = findViewById(R.id.share_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(share_bottom_sheet);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        recyclerView = findViewById(R.id.recyclerview);

        All_Contest_list = (List<ContestActivity>) getIntent().getSerializableExtra("list");

        System.out.println(All_Contest_list.size() + " ******************* 2");

        RecyclerAdapter myAdapter = new RecyclerAdapter(mContext, All_Contest_list, faceook, whatsapp, telegram, googleplus, email, messenger, bottomSheetBehavior, share_bottom_sheet);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(myAdapter);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.smoothScrollToPosition(0);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.contest_layout);

        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "List may corrupted for more accurate result reload the page", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OKAY", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(main_contest.this, MainActivity.class));
    }
}
