package com.example.farid.codersappdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        if(vp_type == 1) v =  inflater.inflate(R.layout.viewpager_guide, container, false);
        else v = inflater.inflate(R.layout.viewpager_guide_2, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*imageView = (ImageView) getView().findViewById(R.id.image);
        imageView.setBackgroundResource(bgRes);*/

        if(vp_type == 1) {
            imageView = getView().findViewById(R.id.view_pager_img);
            title = getView().findViewById(R.id.view_pager_title);
            ans = getView().findViewById(R.id.view_pager_ans);

            imageView.setImageResource(viewPagerAttr.vp_img);
            title.setText(viewPagerAttr.vp_title);
            ans.setText(viewPagerAttr.vp_ans);
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
                            startActivity(intent);
                        }
                    }, 3000);
                }
            });
        }
    }
}
