package com.example.farid.codersappdemo.Adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farid.codersappdemo.R;

public class UserListViewHolder extends RecyclerView.ViewHolder{

    ImageView user_img;
    TextView user_name_tv, user_institute_tv;
    CardView card_view_friend;

    public UserListViewHolder(final View itemView) {
        super(itemView);

        user_img = itemView.findViewById(R.id.user_image);
        user_name_tv = itemView.findViewById(R.id.user_name);
        user_institute_tv = itemView.findViewById(R.id.user_institute);
        card_view_friend = itemView.findViewById(R.id.card_view_friend);
    }
}