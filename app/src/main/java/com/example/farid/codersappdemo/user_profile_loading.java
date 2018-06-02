package com.example.farid.codersappdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class user_profile_loading extends AppCompatActivity {

    public String cf_handle, cc_handle, name, uva_handle;
    user_profile_activity user_activity = new user_profile_activity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_loading);

        getSupportActionBar().hide();

        name = getIntent().getStringExtra("name");
        cf_handle = getIntent().getStringExtra("cf_handle");
        cc_handle = getIntent().getStringExtra("cc_handle");
        uva_handle = getIntent().getStringExtra("uva_handle");

        new BackgroundParse(this).execute();

    }
    public class BackgroundParse extends AsyncTask<Void, Void, Void> {

        Context mContext;
        ProgressDialog dialog;

        BackgroundParse(Context mContext) {
            this.mContext = mContext;
            dialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Intent intent = new Intent(mContext, user_profile.class);
            intent.putExtra("name", name);
            intent.putExtra("list", user_activity);
            startActivity(intent);

            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            CodeforcesInformation(cf_handle);
            CodeChefInformation(cc_handle);

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
