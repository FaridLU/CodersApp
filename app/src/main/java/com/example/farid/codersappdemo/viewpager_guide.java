package com.example.farid.codersappdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farid.codersappdemo.contest.ContestActivity;

import java.util.List;

public class viewpager_guide extends Fragment {
    private int bgRes;
    private ImageView imageView;
    private TextView title, ans;
    user_profile.ViewPagerAttr viewPagerAttr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPagerAttr = (user_profile.ViewPagerAttr) getArguments().getSerializable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*imageView = (ImageView) getView().findViewById(R.id.image);
        imageView.setBackgroundResource(bgRes);*/
        imageView = getView().findViewById(R.id.view_pager_img);
        title = getView().findViewById(R.id.view_pager_title);
        ans = getView().findViewById(R.id.view_pager_ans);

        imageView.setImageResource(viewPagerAttr.vp_img);
        title.setText(viewPagerAttr.vp_title);
        ans.setText(viewPagerAttr.vp_ans);

    }
}
