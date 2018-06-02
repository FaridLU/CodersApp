package com.example.farid.codersappdemo.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.farid.codersappdemo.R;

public class UniversityListViewHolder extends RecyclerView.ViewHolder{

    TextView institute_tv;
    CardView card_view_friend;

    public UniversityListViewHolder(final View itemView) {
        super(itemView);

        institute_tv = itemView.findViewById(R.id.university_name);
        card_view_friend = itemView.findViewById(R.id.card_view_universitiy);
    }
}