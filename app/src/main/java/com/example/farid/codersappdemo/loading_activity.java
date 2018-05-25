package com.example.farid.codersappdemo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.farid.codersappdemo.contest.ContestActivity;
import com.example.farid.codersappdemo.contest.RecyclerAdapter;
import com.example.farid.codersappdemo.contest.main_contest;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.lusfold.spinnerloading.SpinnerLoading;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
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

public class loading_activity extends Fragment {

    private List<ContestActivity> Codeforces_list = new ArrayList<>();
    private List<ContestActivity> CodeChef_list = new ArrayList<>();
    private List<ContestActivity> All_Contest_list = new ArrayList<>();

    AnimatedCircleLoadingView animatedCircleLoadingView;
    BackgroundTask backgroundTask;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_loading, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animatedCircleLoadingView = getView().findViewById(R.id.circle_loading_view);
        startLoading();
        startPercentMockThread();

        backgroundTask = new BackgroundTask(getActivity());
        backgroundTask.execute();
    }


    public class BackgroundTask extends AsyncTask<Void, Void, Void>{

        private Dialog dialog;
        Context mContext;

        BackgroundTask(Context activity){
            dialog = new Dialog(activity);
            mContext = activity;
        }
        @Override
        protected void onPreExecute() {
            // Nothing to write
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //System.out.println(All_Contest_list.size() + "******************");
            Intent intent = new Intent(mContext, main_contest.class);

            intent.putExtra("list", (Serializable) All_Contest_list);
            mContext.startActivity(intent);

            // Helpful link to pass ListView with object = "https://stackoverflow.com/questions/12092612/pass-list-of-objects-from-one-activity-to-other-activity-in-android?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa"
        }
        @Override
        protected Void doInBackground(Void... voids) {

            make_list_for_Codeforces();
            make_list_for_CodeChef();

            Arrange_Contest_List();
            return null;
        }
    }
    private void startLoading() {
        this.animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
    }

    public void resetLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
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
