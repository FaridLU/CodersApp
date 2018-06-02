package com.example.farid.codersappdemo.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farid.codersappdemo.Model.UniversityModel;
import com.example.farid.codersappdemo.R;
import com.example.farid.codersappdemo.UserListFragment;

import java.util.ArrayList;
import java.util.List;

public class UniversityListAdapter extends RecyclerView.Adapter<UniversityListViewHolder> {

    private List<UniversityModel> list = new ArrayList<>();
    private Context mContext;
    private FragmentManager manager;

    public UniversityListAdapter(List<UniversityModel> list, Context mContext, FragmentManager manager) {
        this.list = list;
        this.mContext = mContext;
        this.manager = manager;
    }

    @Override
    public UniversityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.university_list_card_view, parent, false);

        return new UniversityListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UniversityListViewHolder holder, final int position) {
        if (list.get(position).getUniversityName() != null) {
            holder.institute_tv.setText(list.get(position).getUniversityName());

            holder.card_view_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(mContext, UserListActivity.class);
                    intent.putExtra("UNIVERSITY_KEY", list.get(position).getUniversityKey());
                    mContext.startActivity(intent);*/
                    UserListFragment userListFragment = new UserListFragment();
                    Bundle args = new Bundle();
                    args.putString("UNIVERSITY_KEY", list.get(position).getUniversityKey());
                    userListFragment.setArguments(args);

                    manager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, userListFragment).commit();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return (list.size() > 0) ? list.size() : 0;
    }


}
