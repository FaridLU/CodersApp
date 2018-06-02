package com.example.farid.codersappdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.farid.codersappdemo.Model.UserModel;
import com.example.farid.codersappdemo.R;
import com.example.farid.codersappdemo.user_details_activity;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {

    private List<UserModel> list = new ArrayList<>();
    private Context mContext;
    private String universityKey;

    public UserListAdapter(List<UserModel> list, Context mContext, String universityKey) {
        this.list = list;
        this.mContext = mContext;
        this.universityKey = universityKey;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.user_list_card_view, parent, false);

        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserListViewHolder holder, final int position) {

        holder.user_name_tv.setText(list.get(position).getUserName());
        holder.user_institute_tv.setText(list.get(position).getUniversityName());
        Glide.with(mContext).load(list.get(position).getProfilePic()).into(holder.user_img);

        holder.card_view_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = list.get(position).getUserId();

                Intent intent = new Intent(mContext, user_details_activity.class);
                intent.putExtra("USER_KEY", userId);
                intent.putExtra("UNIVERSITY_KEY", universityKey);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list.size() > 0) ? list.size() : 0;
    }


}
