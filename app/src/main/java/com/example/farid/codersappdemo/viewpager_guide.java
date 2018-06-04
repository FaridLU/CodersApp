package com.example.farid.codersappdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farid.codersappdemo.contest.ContestActivity;
import com.example.farid.codersappdemo.submission.sub_recyclerview;
import com.spark.submitbutton.SubmitButton;

import java.util.List;

public class viewpager_guide extends Fragment {
    private int bgRes;
    private ImageView imageView;
    private TextView title, ans;
    private int vp_type;
    private SubmitButton go;
    ViewPagerAttr viewPagerAttr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPagerAttr = (ViewPagerAttr) getArguments().getSerializable("data");
        vp_type = viewPagerAttr.vp_type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        if(vp_type == 1 || vp_type == 2 || vp_type == 3) v =  inflater.inflate(R.layout.viewpager_guide, container, false);
        else v = inflater.inflate(R.layout.viewpager_guide_2, container, false);
        return v;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*imageView = (ImageView) getView().findViewById(R.id.image);
        imageView.setBackgroundResource(bgRes);*/

        int col_newbie, col_pupil, col_specialist, col_expert, col_candidate, col_master, col_intMaster, col_grandmaster;

        if(vp_type <= 3) {
            imageView = getView().findViewById(R.id.view_pager_img);
            title = getView().findViewById(R.id.view_pager_title);
            ans = getView().findViewById(R.id.view_pager_ans);

            imageView.setImageResource(viewPagerAttr.vp_img);
            title.setText(viewPagerAttr.vp_title);
            ans.setText(viewPagerAttr.vp_ans);

            if(viewPagerAttr.vp_title.equals("Max Rank") && !viewPagerAttr.vp_ans.equals("null")) {
                String str = GiveMeCoderType(Integer.valueOf(viewPagerAttr.vp_ans.substring(0, 4)));
                Log.d("strstr", str);
                if(str.equals("Newbie")) ans.setTextColor(Color.parseColor("#FF383939"));
                if(str.equals("Pupil")) ans.setTextColor(Color.parseColor("#009E20"));
                if(str.equals("Specialist")) ans.setTextColor(Color.parseColor("#FF0BC1B5"));
                if(str.equals("Expert")) ans.setTextColor(Color.parseColor("#FF044499"));
                if(str.equals("Candidate Master")) ans.setTextColor(Color.parseColor("#FF760045"));
                if(str.equals("Master")) ans.setTextColor(Color.parseColor("#FFC78100"));
            }


            if(viewPagerAttr.vp_title.equals("Type of Coder") && vp_type == 1) {
                if(viewPagerAttr.vp_ans.equals("Newbie")) ans.setTextColor(Color.parseColor("#FF383939"));
                if(viewPagerAttr.vp_ans.equals("Pupil")) ans.setTextColor(Color.parseColor("#009E20"));
                if(viewPagerAttr.vp_ans.equals("Specialist")) ans.setTextColor(Color.parseColor("#FF0BC1B5"));
                if(viewPagerAttr.vp_ans.equals("Expert")) ans.setTextColor(Color.parseColor("#FF044499"));
                if(viewPagerAttr.vp_ans.equals("Candidate Master")) ans.setTextColor(Color.parseColor("#FF760045"));
                if(viewPagerAttr.vp_ans.equals("Master")) ans.setTextColor(Color.parseColor("#FFC78100"));

            } else if(viewPagerAttr.vp_title.equals("Rating") && vp_type == 1 && !viewPagerAttr.vp_ans.equals("null") && viewPagerAttr.vp_ans != null) {
                Log.d("profile95", viewPagerAttr.vp_ans + "*******");
                String type = GiveMeCoderType(Integer.valueOf(viewPagerAttr.vp_ans));
                if(type.equals("Newbie")) ans.setTextColor(Color.parseColor("#FF383939"));
                if(type.equals("Pupil")) ans.setTextColor(Color.parseColor("#009E20"));
                if(type.equals("Specialist")) ans.setTextColor(Color.parseColor("#FF0BC1B5"));
                if(type.equals("Expert")) ans.setTextColor(Color.parseColor("#FF044499"));
                if(type.equals("Candidate Master")) ans.setTextColor(Color.parseColor("#FF760045"));
                if(type.equals("Master")) ans.setTextColor(Color.parseColor("#FFC78100"));
            } else {
                ans.setTextColor(Color.parseColor("#FF000000"));
            }

        } else {
            imageView = getView().findViewById(R.id.view_pager_judge_img);
            title = getView().findViewById(R.id.view_pager_judge_title);
            go = getView().findViewById(R.id.go_btn);

            imageView.setImageResource(viewPagerAttr.vp_img);
            title.setText(viewPagerAttr.vp_title);
            final int query_ty = viewPagerAttr.vp_index;

            // Query_ty == 1 means mix;
            // Query_ty == 2 means Codeforces;
            // Query_ty == 3 means Codechef;

            final String cf_handle = viewPagerAttr.vp_cf_handle;
            final String cc_handle = viewPagerAttr.vp_cc_handle;
            final String uva_handle = viewPagerAttr.vp_uva_handle;

            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getContext(), sub_recyclerview.class);
                            intent.putExtra("type", query_ty);
                            intent.putExtra("cf_username", cf_handle);
                            intent.putExtra("cc_username", cc_handle);
                            intent.putExtra("uva_handle", uva_handle);
                            Log.d("submissions", "viewpager: "+ cf_handle);
                            startActivity(intent);
                        }
                    }, 3000);
                }
            });
        }
    }
    public String GiveMeCoderType(int rating) {
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

}
