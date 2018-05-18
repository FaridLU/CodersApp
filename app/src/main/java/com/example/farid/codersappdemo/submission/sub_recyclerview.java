package com.example.farid.codersappdemo.submission;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farid.codersappdemo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import thereisnospon.codeview.CodeView;

import static java.lang.Math.min;

public class sub_recyclerview extends AppCompatActivity {

    public List<submission_activity> Mix = new ArrayList<>(), Codeforces = new ArrayList<>(), Codechef = new ArrayList<>(), data = new ArrayList<>();
    public int pos = 0, len = 0;
    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout problem_statement, source_code, bottom_sheet_layout;
    RecyclerView recyclerView;
    private EditText Handle;
    private Button mix, codechef, codeforces;
    private String cf_handle, cc_handle;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    sub_recycler_adapter myAdapter;
    Context mContext;
    Boolean isScrolling = false;
    CodeView codeview;

    int type, currentItems, totalItems, scrollOutItems;

    int ye = 2018, ind = 0; // For Codechef
    int page = 1, index = 1; // For Codeforces


    @SuppressLint("ResourceAsColor")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_recyclerview);
        /*Handle = findViewById(R.id.handle);

        mix = findViewById(R.id.mix);
        codechef = findViewById(R.id.codechef);
        codeforces = findViewById(R.id.codeforces);*/
        progressBar = findViewById(R.id.bottom_progress_bar);
        problem_statement = findViewById(R.id.problem_view);
        source_code = findViewById(R.id.code_view);
        recyclerView = findViewById(R.id.sub_recycler_view);
        bottom_sheet_layout = findViewById(R.id.problem_code_bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.problem_code_bottom_sheet));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        cf_handle = getIntent().getStringExtra("cf_username");
        cc_handle = getIntent().getStringExtra("cc_username");

        type = getIntent().getIntExtra("type", 0);

        progressBar.getIndeterminateDrawable().setColorFilter(R.color.colorPrimaryDark, android.graphics.PorterDuff.Mode.MULTIPLY);
        mContext = sub_recyclerview.this;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = recyclerView.getLayoutManager().getChildCount();
                totalItems = recyclerView.getLayoutManager().getItemCount();
                scrollOutItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();;

                if(isScrolling && (currentItems + scrollOutItems == totalItems) && (dy > 0) && type != 1) { // dy > 0 means scrolling down
                    isScrolling = false;
                    new BackgroundParsing(sub_recyclerview.this, 2, type).execute();
                }
            }
        });

        if(type == 1) { // Mix
            ye = 2018; ind = 0; page = 1; index = 1;
            myAdapter = new sub_recycler_adapter(mContext, Handle, Mix, bottomSheetBehavior, problem_statement, source_code, bottom_sheet_layout);
            layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            new BackgroundParsing(sub_recyclerview.this, 1, 1).execute();
        } else if(type == 2) { // Codeforces
            ye = 2018; ind = 0; page = 1; index = 1;
            myAdapter = new sub_recycler_adapter(mContext, Handle, Codeforces, bottomSheetBehavior, problem_statement, source_code, bottom_sheet_layout);
            layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            new BackgroundParsing(sub_recyclerview.this, 1, 2).execute();
        } else if(type == 3) { // Codechef
            ye = 2018; ind = 0; page = 1; index = 1;
            myAdapter = new sub_recycler_adapter(mContext, Handle, Codechef, bottomSheetBehavior, problem_statement, source_code, bottom_sheet_layout);
            layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(new LinearLayoutManager(sub_recyclerview.this));
            recyclerView.setAdapter(myAdapter);

            new BackgroundParsing(sub_recyclerview.this, 1, 3).execute();
        } else if(type == 4) { // UVA

        }
    }

    public class BackgroundParsing extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        Context mContext;
        int progress_type, len, judge_type;
        BackgroundParsing(Context activity, int progress_type, int judge_type) {
            dialog = new ProgressDialog(activity);
            mContext = activity;
            this.progress_type = progress_type;
        }

        @Override
        protected void onPreExecute() {
            if(progress_type == 1) {
                dialog.setTitle("Loading...");
                dialog.setMessage("Please wait.");
                dialog.show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            progressBar.setVisibility(View.GONE);

            if(judge_type == type && Codechef.size() == 0) Toast.makeText(mContext, "May be Codechef server is down! Try agin Later", Toast.LENGTH_SHORT).show();
            if(judge_type == type && Codeforces.size() == 0) Toast.makeText(mContext, "May be Codeforces server is down! Try agin Later", Toast.LENGTH_SHORT).show();

            if(judge_type == type && len == Codechef.size()) Toast.makeText(mContext, "Seems like all activities are already loaded!", Toast.LENGTH_SHORT).show();
            if(judge_type == type && len == Codeforces.size()) Toast.makeText(mContext, "Seems like all activities are already loaded!", Toast.LENGTH_SHORT).show();

            bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.problem_code_bottom_sheet));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            myAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(type == 1) { // Code for Mix
                Mix.addAll(make_for_mix());
            } else if(type == 2) { // Code for Codeforces
                len = Codeforces.size();
                Codeforces.addAll(make_for_codeforces());
            } else if(type == 3) { // Code for Codechef
                len = Codechef.size();
                Codechef.addAll(make_for_codechef());
            }
            return null;
        }

    }
    public List make_for_mix() {
        List<submission_activity> list = new ArrayList<>();
        List<submission_activity> main_list = new ArrayList<>();
        List<Time_Class> tmp_list = new ArrayList<>();

        list.addAll(make_for_codechef());
        list.addAll(make_for_codechef());
        list.addAll(make_for_codechef());
        list.addAll(make_for_codechef());
        list.addAll(make_for_codechef());
        list.addAll(make_for_codechef());

        int min, hour, day = 0, month, year;
        String time;
        int le = list.size();
        for(int i=0; i<list.size(); i++) { // For Codechef
            time = list.get(i).solution_time;

            hour = (time.charAt(0) - '0')*10 + (time.charAt(1)-'0');
            min = (time.charAt(3) - '0')*10 + (time.charAt(4)-'0');
            if(time.substring(6, 8). equals("PM")) hour += 12;
            day = (time.charAt(9) - '0')*10 + (time.charAt(10)-'0');
            month = (time.charAt(12) - '0')*10 + (time.charAt(13)-'0');
            year = 2000 + (time.charAt(15) - '0')*10 + (time.charAt(16)-'0');

            tmp_list.add(new Time_Class(i, min, hour, day, month, year));
        }

        for(int i=le; i<list.size(); i++) { // For Codeforces
            time = list.get(i).solution_time;

            hour = (time.charAt(0) - '0')*10 + (time.charAt(1)-'0');
            min = (time.charAt(3) - '0')*10 + (time.charAt(4)-'0');
            year = (time.charAt(9) - '0')*1000 + (time.charAt(10) - '0')*100 + (time.charAt(11) - '0')*10 + (time.charAt(12) - '0');
            month = (time.charAt(14) - '0')*10 + (time.charAt(15)-'0');
            day = (time.charAt(17) - '0')*10 + (time.charAt(18)-'0');

            tmp_list.add(new Time_Class(i, min, hour, day, month, year));
        }

        Collections.sort(tmp_list, new Comparator<Time_Class>() {
            @Override
            public int compare(Time_Class x, Time_Class y) {
                System.out.println(x.year + " " + x.month + " " + x.day + " " + x.hour + " " + x.min);
                System.out.println(y.year + " " + y.month + " " + y.day + " " + y.hour + " " + y.min);
                System.out.println();

                if(x.year == y.year) {
                    if(x.month == y.month) {
                        if(x.day == y.day) {
                            if(x.hour == y.hour) {
                                return x.min > y.min ? -1 : 1;
                            }
                            return x.hour > y.hour ? -1 : 1;
                        }
                        return x.day > y.day ? -1 : 1;
                    }
                    return x.month > y.month ? -1 : 1;
                }
                return x.year > y.year ? -1 : 1;
            }
        });

        for(int i=0; i< min(50,tmp_list.size()); i++) {

            main_list.add(list.get(tmp_list.get(i).ind));
        }

        return main_list;
    }
    public List make_for_codeforces() {
        List <submission_activity>list = new ArrayList<>();
        try {
            int limit = Integer.valueOf(Jsoup.connect("http://codeforces.com/submissions/"+cf_handle+"/page/1").get().getElementsByClass("pagination").select("ul").select("li").get(5).text());
            int val = 0;


            for(int i=1; i<=limit ; i++) {

                String url = "http://codeforces.com/submissions/"+cf_handle+"/page/"+i;
                Document doc = (Document) Jsoup.connect(url).get();
                Elements el = doc.getElementsByClass("status-frame-datatable").select("tbody").select("tr");
                //System.out.println(el.size());

                String solution_id, solution_time,  problem_name, solution_status,  problem_link, solution_link, judge, soulution_language, solution_execution_time, usage_memory, problem_difficulty;

                for(int j= (page == i) ? index : 1; j<el.size(); j++) {
                    page = i;
                    index = j+1;

                    submission_activity store = new submission_activity();
                    Elements tmp = el.get(j).select("td");
                    String s[] = tmp.text().toString().split(" ");

                    store.solution_id = s[0];
                    store.solution_time = s[2]+" "+s[1];
                    store.problem_name = tmp.get(3).select("a").text();
                    store.solution_status = tmp.get(5).text();
                    store.problem_link = tmp.get(3).select("a").attr("abs:href");
                    store.solution_link = store.problem_link.substring(0,store.problem_link.indexOf("problem"))+"submission/"+tmp.get(0).text() ;
                    store.judge = "codeforces";
                    store.soulution_language = tmp.get(4).text();
                    store.solution_execution_time = tmp.get(6).text();
                    store.usage_memory = tmp.get(7).text();
                    store.problem_difficulty = "Entry oise na";
                    store.handle = cf_handle;
                    if(type == 2) Codeforces.add(store);
                    else list.add(store);
                    val++;
                    if(val >= 20) break;
                }
                if(val >= 20) break;
            }
        } catch (Exception e) {
            Log.d("catch: ", "codeforces");
        } finally{
            return list;
        }
    }
    public List make_for_codechef() {
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
        List<submission_activity> list = new ArrayList<>();
        try {
            int val = 0;
            for(int y=ye; ; y--) {
                String year = Integer.toString(y);

                String url = "https://www.codechef.com/submissions?sort_by=All&sorting_order=asc&language=All&status=All&year="+year+"&handle="+cc_handle+"&pcode=&ccode=&Submit=GO";
                Document doc = (Document) Jsoup.connect(url).get();

                Elements el = doc.getElementsByClass("dataTable").select("tbody").select("tr");

                for(int i = (ye == y) ? ind: 0; i<el.size(); i++) {
                    ye = y;
                    ind = i+1;
                    val++;

                    String solution_id = el.get(i).select("td").get(0).text();
                    String solution_time = el.get(i).select("td").get(1).text();
                    String problem_link = el.get(i).select("td").get(4).select("a").attr("abs:href").toString();
                    String problem_code = el.get(i).select("td").get(3).select("a").text();
                    String problem_difficulty = el.get(i).select("td").get(4).select("a").text();
                    String solution_status = el.get(i).select("td").get(5).select("div").select("span").first().attr("title").toString();
                    String solution_execution_time = el.get(i).select("td").get(6).text();
                    String usage_memory = el.get(i).select("td").get(7).text();
                    String solution_language = el.get(i).select("td").get(8).text();
                    String solution_link = el.get(i).select("td").get(9).select("a").attr("abs:href").toString();

                    if(solution_status.length() == 0) {
                        String point =  el.get(i).select("td").get(5).select("div").select("span").first().text();
                        StringTokenizer st = new StringTokenizer(point);
                        point = st.nextToken();
                        if(Integer.valueOf(point) == 100) {
                            solution_status = "accepted";
                        }
                        else solution_status = "partially accepted("+point+"pts)";
                    }
                    if(!solution_status.equals("accepted")) {

                        solution_execution_time = "-";
                        usage_memory = "-";
                    }
                    String tmp = solution_link;
                    int len = solution_link.length();

                    solution_link = tmp.replaceFirst("viewsolution", "viewplaintext");

                    if(y == 2018 && i == 0) {
                        System.out.println(solution_link);
                    }
                    if(type == 3) Codechef.add( new submission_activity(cc_handle, solution_id, solution_time, problem_code, solution_status, problem_link, solution_link, "codechef", solution_language, solution_execution_time, usage_memory, problem_difficulty)) ;
                    else list.add(new submission_activity(cc_handle, solution_id, solution_time, problem_code, solution_status, problem_link, solution_link, "codechef", solution_language, solution_execution_time, usage_memory, problem_difficulty));
                    if(val >= 20) break;
                }
                if(val >= 20) break;
            }
        } catch(Exception e) {
            System.out.println("Catch ");
        } finally {
            return list;
        }
           /* }
        }).start();*/
    }
}
class Time_Class {
    int ind, min, hour, day, month, year;
    Time_Class(int ind, int min, int hour, int day, int month, int year){
        this.ind = ind;
        this.min = min;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
