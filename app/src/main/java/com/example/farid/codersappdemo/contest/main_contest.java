package com.example.farid.codersappdemo.contest;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baoyz.widget.PullRefreshLayout;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.stone.vega.library.VegaLayoutManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public LinearLayout faceook, whatsapp, telegram, email, googleplus, messenger, bottom_she;
    public RelativeLayout share_bottom_sheet;
    public BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;
    private Context mContext = this;
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    CoordinatorLayout coordinatorLayout;
    SweetSheet mSweetSheet3;
    private int position;
    PullRefreshLayout pullRefreshLayout;
    RecyclerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contest);
        setTitle("Contest's List");

        FindVIewByID();

        RelativeLayout rl = findViewById(R.id.contest_layout);

        All_Contest_list = (List<ContestActivity>) getIntent().getSerializableExtra("list");

        mSweetSheet3 = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true, CustomDelegate.AnimationType.DuangLayoutAnimation);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_share, null, false);
        customDelegate.setCustomView(view);
        customDelegate.setContentHeight(775);
        mSweetSheet3.setDelegate(customDelegate);
        mSweetSheet3.setBackgroundEffect(new BlurEffect(8));

        SetAdapter();

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //pullRefreshLayout.setRefreshing(true);
                new BackgroundRefreshTask().execute();
            }
        });

        view.findViewById(R.id.facebook_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();
                Intent shareIntent = ShareCompat.IntentBuilder.from(main_contest.this)
                        .setText(All_Contest_list.get(position).contest_link)
                        .setType("text/plain")
                        .getIntent()
                        .setPackage("com.facebook.katana");
                try {
                    startActivity(shareIntent);
                } catch (Exception e) {
                    Toast.makeText(main_contest.this,"Please Install Facebook app", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(main_contest.this, "Facebook share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.twitter_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();
                Intent shareIntent = ShareCompat.IntentBuilder.from(main_contest.this)
                        .setText(All_Contest_list.get(position).contest_link)
                        .setType("text/plain")
                        .getIntent()
                        .setPackage("com.twitter.android");
                try {
                    startActivity(shareIntent);
                } catch (Exception e) {
                    Toast.makeText(main_contest.this,"Please Install Twitter app", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(main_contest.this, "Facebook share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.whatsapp_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(main_contest.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(main_contest.this, "Whatsapp share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.telegram_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,All_Contest_list.get(position).contest_link);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("org.telegram.messenger");
                try{
                    startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(),"Install Telegram",Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(main_contest.this, "Telegram share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.messenger_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,All_Contest_list.get(position).contest_link);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(main_contest.this,"Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(main_contest.this, "Messenger share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.googleplus_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();

                Intent shareIntent = ShareCompat.IntentBuilder.from(main_contest.this)
                        .setText(All_Contest_list.get(position).contest_link)
                        .setType("text/plain")
                        .getIntent()
                        .setPackage("com.google.android.apps.plus");
                try {
                    startActivity(shareIntent);
                } catch (Exception e) {
                    Toast.makeText(main_contest.this,"Please Install Google plus app", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(main_contest.this, "Google+ share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.message_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();

                Intent shareIntent = ShareCompat.IntentBuilder.from(main_contest.this)
                        .setText(All_Contest_list.get(position).contest_link)
                        .setType("text/plain")
                        .getIntent()
                        .setPackage("com.samsung.android.messaging");
                try {
                    startActivity(shareIntent);
                } catch (Exception e) {
                    Toast.makeText(main_contest.this,"Please Install Google plus app", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(main_contest.this, "Google+ share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.email_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.toggle();
                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, All_Contest_list.get(position).contest_link);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*/

                Intent shareIntent = ShareCompat.IntentBuilder.from(main_contest.this)
                        .setText(All_Contest_list.get(position).contest_link)
                        .setType("text/plain")
                        .getIntent()
                        .setPackage("com.google.android.gm");
                try {
                    startActivity(shareIntent);
                } catch (Exception e) {
                    Toast.makeText(main_contest.this,"Please Install Email app", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(main_contest.this, "Email share button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        /*final Snackbar snackbar = Snackbar.make(coordinatorLayout, "List may corrupted for more accurate result reload the page", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OKAY", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();*/

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReciver, new IntentFilter("Pass"));
    }
    public BroadcastReceiver mMessageReciver = new BroadcastReceiver() {
        // Helpful link for this: https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        @Override
        public void onReceive(Context context, Intent intent) {
            position = intent.getIntExtra("position", 0);
        }
    };
    public void SetAdapter(){
        myAdapter = new RecyclerAdapter(mContext, All_Contest_list, faceook, whatsapp, telegram, googleplus, email, messenger, mSweetSheet3, share_bottom_sheet);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(myAdapter);
        recyclerView.smoothScrollToPosition(0);
        //recyclerView.setNestedScrollingEnabled(false); // Hide
    }
    public void FindVIewByID(){
        View view = LayoutInflater.from(main_contest.this).inflate(R.layout.bottom_sheet_share, null, false);

        faceook = view.findViewById(R.id.facebook_share);
        whatsapp = view.findViewById(R.id.whatsapp_share);
        telegram = view.findViewById(R.id.telegram_share);
        googleplus = view.findViewById(R.id.googleplus_share);
        messenger = view.findViewById(R.id.messenger_share);
        email = view.findViewById(R.id.email_share);
        share_bottom_sheet = view.findViewById(R.id.share_bottom_sheet);
        recyclerView = findViewById(R.id.recyclerview);
        pullRefreshLayout = findViewById(R.id.pullRefresh);
    }

    @Override
    public void onBackPressed() {
        if (mSweetSheet3.isShow()) {
            mSweetSheet3.dismiss();
        } else {
            startActivity(new Intent(main_contest.this, MainActivity.class));
        }
    }
    public class BackgroundRefreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Do Something before showing result
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pullRefreshLayout.setRefreshing(false);
            myAdapter.notifyDataSetChanged();
            // After complete sync
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Do somthing while collecting data
            All_Contest_list = new ArrayList<>();
            make_list_for_Codeforces();
            make_list_for_CodeChef();
            Arrange_Contest_List();
            return null;
        }
    }

    public void Arrange_Contest_List() {
        Collections.sort(All_Contest_list, new Comparator<ContestActivity>() {
            @Override
            public int compare(ContestActivity x, ContestActivity y) {

                int Start_Day_X = Integer.valueOf(x.start_date.substring(0, 2));
                int Start_Day_Y = Integer.valueOf(y.start_date.substring(0, 2));

                int Start_Month_X = Month(x.start_information.substring(0, 3));
                int Start_Month_Y = Month(y.start_information.substring(0, 3));

                int Start_Year_X = Integer.valueOf(x.start_information.substring(4, 8));
                int Start_Year_Y = Integer.valueOf(y.start_information.substring(4, 8));

                int Start_Hour_X = Integer.valueOf(x.start_information.substring(9, 11));
                int Start_Hour_Y = Integer.valueOf(y.start_information.substring(9, 11));

                int Start_Min_X = Integer.valueOf(x.start_information.substring(12, 14));
                int Start_Min_Y = Integer.valueOf(y.start_information.substring(12, 14));


                if (x.contest_type == y.contest_type) {
                    if (Start_Year_X == Start_Year_Y) {
                        if (Start_Month_X == Start_Month_Y) {
                            if (Start_Day_X == Start_Day_Y) {
                                if (Start_Hour_X == Start_Hour_Y) {
                                    return Start_Min_X < Start_Min_Y ? -1 : 1;
                                }
                                return Start_Hour_X < Start_Hour_Y ? -1 : 1;
                            }
                            return Start_Day_X < Start_Day_Y ? -1 : 1;
                        }
                        return Start_Month_X < Start_Month_Y ? -1 : 1;
                    }
                    return Start_Year_X < Start_Year_Y ? -1 : 1;
                }
                return x.contest_type < y.contest_type ? -1 : 1;
            }
        });
    }

    public void make_list_for_Codeforces() {
        //System.out.println("Codefoces -> ");
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
        try {
            String url = "http://codeforces.com/contests?complete=true";
            Document doc = Jsoup.connect(url).get();
            Elements el = doc.getElementsByClass("datatable").get(0).select("tbody").select("tr");

            int len = el.size();

            for (int i = 1; i < len; i++) {

                String contest_name = el.get(i).select("td").get(0).text(); // Contest_Name
                String contest_date = el.get(i).select("td").get(2).text();
                String contest_duration = el.get(i).select("td").get(3).text();
                String contest_link = null;

                int type = 2;

                if(contest_name.endsWith("Enter »")) {
                    type = 1;
                    int ln = contest_name.length();
                    contest_name = contest_name.substring(0, ln-7);
                    contest_link = el.get(i).select("td").get(0).select("a").attr("abs:href");
                }

                String start_date = contest_date.substring(4, 6);
                String start_information = contest_date.replaceFirst("/" + start_date + "/", " ");

                String end_date_information = GiveMeEnd(contest_date, contest_duration);

                if (end_date_information.length() == 0) continue;

                String end_date = end_date_information.substring(8, 10);
                int le = end_date_information.length();

                String end_information = end_date_information.substring(4, 7) + " " + end_date_information.substring(le - 4, le) + " " + end_date_information.substring(11, 20);
                end_information = end_information.substring(0, end_information.length()-4);

                All_Contest_list.add(new ContestActivity(start_date, start_information, end_date, end_information, contest_name, contest_link, R.drawable.codeforces_round, type, "codeforces"));
                //System.out.println("Codefoces -> 2 ");
            }

        } catch (Exception e) {

        }

            /*}
        }).start();*/
    }

    public void make_list_for_CodeChef() {

        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
        try {
            String url = "https://www.codechef.com/contests";
            String url2 = "https://www.codechef.com/";

            Document doc = Jsoup.connect(url).get();
            Elements el = doc.getElementsByClass("dataTable");

            for(int k=0; k<2; k++) {
                Elements table = el.get(k).select("tbody");
                Elements rows = table.select("tr");

                for(int i=0; i<rows.size(); i += 1) {

                    Element row0 = rows.get(i);
                    Elements cols0 = row0.select("td");

                    String tmp;
                    String ContestName = row0.select("td").select("a").text();

                    tmp = row0.select("td").text();

                    String[] arr = new String[10];
                    int ind = -1;
                    String without_ContestName = "";
                    for(int j=0; j<tmp.length(); j++) {
                        int cInd = 0, tInd = j;

                        if(tmp.charAt(j) == ' ') {
                            arr[++ind] = without_ContestName;
                            without_ContestName = "";
                            continue;
                        }
                        int len = ContestName.length();

                        while(cInd < len && ContestName.charAt(cInd) == (tmp.charAt(tInd))) {
                            cInd++; tInd++;
                        }
                        if(cInd == len) j = tInd;
                        else {
                            without_ContestName += tmp.charAt(j);
                        }
                    }
                    arr[++ind] = without_ContestName;

                    String ContestLink = url2 + "/" + arr[0];
                    String StartDate = arr[2] + " " + arr[3];
                    String StartTime = arr[4];
                    String EndDate = arr[6] + " " + arr[7];
                    String EndTime = arr[8];

                    EndTime = EndTime.substring(0, 5);
                    StartTime = StartTime.substring(0, 5);

                    All_Contest_list.add(new ContestActivity(arr[1], StartDate + " " + StartTime, arr[5], EndDate + " " + EndTime , ContestName, ContestLink, R.drawable.codechef_round, k+1, "codechef"));
                }
            }
        }catch (Exception e) {

        }
            /*}
        }).start();*/
    }

    public static String GiveMeEnd(String start_date, String duration) {

        Helpful_Codes(); // Nothing but useful

        System.out.println(start_date + " " + duration);

        int year = Integer.valueOf(start_date.substring(7, 11));
        int month = Month(start_date.substring(0, 3));
        int date = Integer.valueOf(start_date.substring(4, 6));
        int hour = Integer.valueOf(start_date.substring(12, 14));
        int min = Integer.valueOf(start_date.substring(15, 17));
        int len = duration.length();
        int duration_min = 0, duration_hour = 0, duration_day = 0;

        if(len-2 >= 0) {
            duration_min = Integer.valueOf(duration.substring(len-2, len));
            len -= 2;
        }
        else if(len == 1) {
            duration_min = Integer.valueOf(duration.substring(len-1, len));
            len--;
        }
        len--;

        if(len-2 >= 0) {
            duration_hour = Integer.valueOf(duration.substring(len-2, len));
            len -= 2;
        }
        else if(len == 1) {
            duration_hour = Integer.valueOf(duration.substring(len-1, len));
            len--;
        }
        len--;

        if(len-2 >= 0) {
            duration_day = Integer.valueOf(duration.substring(len-2, len));
            len -= 2;
        }
        else if(len == 1) {
            duration_day = Integer.valueOf(duration.substring(len-1, len));
            len--;
        }
        len--;

        DateFormat format = new SimpleDateFormat("MMM/dd/yyyy HH:mm", Locale.US);

        String End_Time = null;
        String ans = "kunta nai";

        try {

            Date parse_date = format.parse(start_date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse_date);

            System.out.println(parse_date.toString());
            calendar.add(Calendar.MINUTE, duration_min);
            calendar.add(Calendar.HOUR, duration_hour);
            calendar.add(Calendar.DAY_OF_MONTH, duration_day);

            End_Time = calendar.getTime().toString();
            ans = "aise";

        }catch(Exception e) {
            System.out.println("Something Went wrong");
        }
        //System.out.println(End_Time);

        return End_Time;
    }

    public static int Month(String month) {
        if(month.toLowerCase().equals("jan")) return 1;
        if(month.toLowerCase().equals("feb")) return 2;
        if(month.toLowerCase().equals("mar")) return 3;
        if(month.toLowerCase().equals("apr")) return 4;
        if(month.toLowerCase().equals("may")) return 5;
        if(month.toLowerCase().equals("jun")) return 6;
        if(month.toLowerCase().equals("jul")) return 7;
        if(month.toLowerCase().equals("aug")) return 8;
        if(month.toLowerCase().equals("sep")) return 9;
        if(month.toLowerCase().equals("oct")) return 10;
        if(month.toLowerCase().equals("nov")) return 11;
        if(month.toLowerCase().equals("dec")) return 12;
        return 0;
    }

    public static void Helpful_Codes(){
        /**
         Helpful Links:
         --------------
         http://www.digitstory.com/recyclerview-multiple-viewholders/
         https://developer.android.com/reference/java/text/SimpleDateFormat.html
         https://stackoverflow.com/questions/22917562/adding-n-number-of-days-in-a-given-particular-date
         https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         https://stackoverflow.com/questions/18733582/calculating-a-future-date?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         */
    }
}
