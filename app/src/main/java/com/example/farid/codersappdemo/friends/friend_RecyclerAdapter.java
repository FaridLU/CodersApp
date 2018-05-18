package com.example.farid.codersappdemo.friends;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farid.codersappdemo.MainActivity;
import com.example.farid.codersappdemo.R;
import com.example.farid.codersappdemo.user_profile;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class friend_RecyclerAdapter extends RecyclerView.Adapter<friend_RecyclerAdapter.friend_RecyclerViewHolder>{

    private List<friend_list.FriendActivity>list = new ArrayList<>();
    private Context mContext;

    public friend_RecyclerAdapter(List<friend_list.FriendActivity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public friend_RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.card_view_friend_list, parent, false);

        return new friend_RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final friend_RecyclerViewHolder holder, final int position) {
        holder.friend_name_tv.setText(list.get(position).getFriend_name());
        holder.friend_institute_tv.setText(list.get(position).getFriend_institue());
        holder.friend_img.setImageResource(list.get(position).getFriend_image());

        holder.card_view_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, user_profile.class);
                intent.putExtra("name", list.get(position).getFriend_name());
                intent.putExtra("cf_handle", list.get(position).getCodeforcesID());
                intent.putExtra("cc_handle", list.get(position).getCodechefID());
                mContext.startActivity(intent);

                /*Fragment mFragment = null;
                mFragment = new user_profile();

                FragmentActivity context = (FragmentActivity) mContext;

                FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_container, mFragment);
                transaction.commit();*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return (list.size() > 0) ? list.size() : 0;
    }

    public class friend_RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView friend_img;
        TextView friend_name_tv, friend_institute_tv;
        CardView card_view_friend;

        public friend_RecyclerViewHolder(final View itemView) {
            super(itemView);

            friend_img = itemView.findViewById(R.id.friend_image);
            friend_name_tv = itemView.findViewById(R.id.friend_name);
            friend_institute_tv = itemView.findViewById(R.id.friend_institute);
            card_view_friend = itemView.findViewById(R.id.card_view_friend);
        }
    }

}
