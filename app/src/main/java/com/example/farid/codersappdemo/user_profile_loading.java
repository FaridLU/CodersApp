package com.example.farid.codersappdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.farid.codersappdemo.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.time.LocalDate;

public class user_profile_loading extends AppCompatActivity {

    private String cf_handle, cc_handle, name, uva_handle;
    private user_profile_activity user_activity = new user_profile_activity();
    private String key, userId;
    private DatabaseReference mUniversityWiseUserListRef, mUniversityListRef ;
    private ValueEventListener userDataListener;
    private BackgroundParse backgroundParse;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_loading);

        getSupportActionBar().hide();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.setMessage("Please wait.");
        dialog.show();

        name = getIntent().getStringExtra("name");
        cf_handle = getIntent().getStringExtra("cf_handle");
        cc_handle = getIntent().getStringExtra("cc_handle");
        uva_handle = getIntent().getStringExtra("uva_handle");

        Log.d("submissions", "user_profile_loading: " + cf_handle);

        int type = getIntent().getIntExtra("type", 2);

        backgroundParse = new BackgroundParse(this);


        // Parse user details if it this activity called from userList Adapter
        initView();
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && type == 2) {
            key = bundle.getString("UNIVERSITY_KEY", "");
            userId = bundle.getString("USER_KEY", FirebaseAuth.getInstance().getCurrentUser().getUid());

            if (key != "") {
                Log.d("tpstps", "CF Handle: " + cf_handle);
                mUniversityWiseUserListRef = FirebaseDatabase.getInstance().getReference().child("universityWiseUserList").child(key).child(userId);
                mUniversityWiseUserListRef.addValueEventListener(userDataListener);
                Log.d("DATA_Ana", "ono aise");

            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backgroundParse.execute();
            }
        }, 2000);
    }

    private void initView() {

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUniversityWiseUserListRef = FirebaseDatabase.getInstance().getReference().child("universityWiseUserList");
        mUniversityListRef = FirebaseDatabase.getInstance().getReference().child("universityList");

        userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserModel model = dataSnapshot.getValue(UserModel.class);

                    Log.v("DATA_Ana", "userName: " + model.getCfHandle());

                    name = model.getUserName();
                    uva_handle = model.getUvaHandle();
                    cf_handle = model.getCfHandle();
                    cc_handle = model.getCodechefHandle();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public class BackgroundParse extends AsyncTask<Void, Void, Void> {

        Context mContext;


        BackgroundParse(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(mContext, user_profile.class);
            intent.putExtra("name", name);
            intent.putExtra("list", user_activity);
            intent.putExtra("cf_handle", cf_handle);
            intent.putExtra("cc_handle", cc_handle);
            intent.putExtra("uva_handle", uva_handle);
            intent.putExtra("USER_KEY", userId);
            intent.putExtra("UNIVERSITY_KEY", key);
            startActivity(intent);
            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.v("DATA_Ana", "Async task userName: " + cf_handle);
            CodeforcesInformation(cf_handle);
            CodeChefInformation(cc_handle);
            Log.v("DATA_Ana", "Async task list Size: " + user_activity.cf_rating);

            return null;
        }
    }

    void CodeforcesInformation(String userID) {
        try {
            Document doc = Jsoup.connect("http://codeforces.com/profile/"+userID).get();

            String rating = doc.getElementsByClass("info").select("ul").select("li").select("span").get(0).text();
            String MaxType = doc.getElementsByClass("info").select("ul").select("li").select("span").get(2).text();
            String MaxRank  = doc.getElementsByClass("info").select("ul").select("li").select("span").get(1).select("span").get(2).text();


            doc = Jsoup.connect("http://codeforces.com/submissions/_FariD_").get();
            String ans  = doc.getElementsByClass("page-index").last().text();

            int totalSubmissions = (Integer.valueOf(ans)-1)*50;

            doc = Jsoup.connect("http://codeforces.com/submissions/"+userID+"/page/"+ans).get();
            totalSubmissions += doc.getElementsByClass("status-frame-datatable").select("tbody").select("tr").size();


            user_activity.cf_rating = rating;
            user_activity.cf_maxRank = MaxRank + " (" + GiveMeCoderType(Integer.valueOf(MaxRank)) + ")";
            user_activity.cf_totalSubmissions = Integer.toString(totalSubmissions);
            user_activity.cf_typeOfCoder = GiveMeCoderType(Integer.valueOf(rating));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String GiveMeCoderType(int rating) {
        if(rating <= 1199) return "Newbie";
        else if(rating <= 1399) return "Pupil";
        else if(rating <= 1599) return "Specialist";
        else if(rating <= 1899) return "Expert";
        else if(rating <= 2199) return "Candidate Master";
        else if(rating <= 2299) return "Master";
        else if(rating <= 2399) return "International Master";
        else if(rating <= 2599) return "Grandmaster";
        else if(rating <= 2899) return "International Grandmaster";
        else return "Legendary Grandmaster";
    }

    void CodeChefInformation(String handle) {
        try {
            Document doc = Jsoup.connect("https://www.codechef.com/users/"+handle).get();

            String normalCCrating = doc.getElementsByClass("rank-stats").get(0).text();
            String LongChallengeRating = doc.getElementsByClass("rank-stats").get(1).text();
            String CookOffRating = doc.getElementsByClass("rank-stats").get(2).text();
            String FullySolved = doc.getElementsByClass("rating-data-section problems-solved").select("h5").get(0).text();
            String PartiallySolved = doc.getElementsByClass("rating-data-section problems-solved").select("h5").get(1).text();
            String TypeOfCoder = doc.getElementsByClass("rating").get(0).text();
            String GlobalRank = doc.getElementsByClass("inline-list").get(1).select("li").get(0).text();
            String CountryRank = doc.getElementsByClass("inline-list").get(1).select("li").get(1).text();

            String ans = "";
            ans += normalCCrating.charAt(0);
            ans += normalCCrating.charAt(1);
            ans += normalCCrating.charAt(2);
            ans += normalCCrating.charAt(3);
            normalCCrating = ans;

            ans = "";
            ans += LongChallengeRating.charAt(0);
            ans += LongChallengeRating.charAt(1);
            ans += LongChallengeRating.charAt(2);
            ans += LongChallengeRating.charAt(3);
            LongChallengeRating = ans;

            ans = "";
            ans += CookOffRating.charAt(0);
            ans += CookOffRating.charAt(1);
            ans += CookOffRating.charAt(2);
            ans += CookOffRating.charAt(3);
            CookOffRating = ans;


            ans = FullySolved.substring(FullySolved.indexOf("(")+1,FullySolved.indexOf(")"));
            FullySolved = ans;

            ans = PartiallySolved.substring(PartiallySolved.indexOf("(")+1,PartiallySolved.indexOf(")"));
            PartiallySolved = ans;

            ans = "";
            ans += GlobalRank.charAt(0);
            ans += GlobalRank.charAt(1);
            ans += GlobalRank.charAt(2);
            ans += GlobalRank.charAt(3);
            GlobalRank = ans;

            ans = "";
            ans += CountryRank.charAt(0);
            ans += CountryRank.charAt(1);
            ans += CountryRank.charAt(2);
            ans += CountryRank.charAt(3);
            CountryRank = ans;

            user_activity.cc_countryRank = CountryRank;
            user_activity.cc_rating = normalCCrating;
            user_activity.cc_totalSolved = FullySolved;
            user_activity.cc_typeOfCoder = TypeOfCoder;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
