package com.example.farid.codersappdemo.submission;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farid.codersappdemo.R;

import java.util.ArrayList;
import java.util.List;

public class sub_recycler_adapter extends RecyclerView.Adapter<sub_recycler_adapter.sub_recycler_viewHolder>{
    private TextView username;
    List<submission_activity> list = new ArrayList<>();
    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout problem_statement, source_code, bottom_sheet_layout;
    Context mContext;

    public sub_recycler_adapter(Context mContext, TextView username, List<submission_activity> list, BottomSheetBehavior bottomSheetBehavior, LinearLayout problem_statement, LinearLayout source_code, LinearLayout bottom_sheet_layout) {
        this.mContext = mContext;
        this.username = username;
        this.list = list;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.problem_statement = problem_statement;
        this.source_code = source_code;
        this.bottom_sheet_layout = bottom_sheet_layout;
    }

    @Override
    public sub_recycler_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.sub_cardview, parent, false);

        TextView tv = view.findViewById(R.id.solution_status);

        if(viewType == 1) tv.setTextColor(Color.parseColor("#00AA00"));
        if(viewType == 2) tv.setTextColor(Color.parseColor("#64DD17"));
        if(viewType == 3) tv.setTextColor(Color.parseColor("#FF0000"));
        if(viewType == 4) tv.setTextColor(Color.parseColor("#0000FF"));
        if(viewType == 5) tv.setTextColor(Color.parseColor("#00AAAA"));

        return new sub_recycler_viewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        String status = list.get(position).solution_status;

        if(status.startsWith("accepted") || status.startsWith("Accepted")) return 1;
        if(status.startsWith("partially")) return 2;
        if(status.startsWith("wrong") || status.startsWith("Wrong")) return 3;
        if(status.startsWith("time") || status.startsWith("Time")) return 4;
        if(status.startsWith("runtime") || status.startsWith("Runtime")) return 5;
        return 2;
    }

    @Override
    public void onBindViewHolder(sub_recycler_viewHolder holder, final int position) {
        int type = 0;
        // Type = 1 (Codeforces)
        // Type = 2 (CodeChef)
        if(list.get(position).getJudge() == "codeforces") type = 1;
        else type = 2;
        if(type == 1)  holder.image_judge.setImageResource(R.drawable.codeforces_round_icon);
        else if(type == 2) holder.image_judge.setImageResource(R.drawable.codechef_round_icon);
        holder.problem_name.setText(list.get(position).problem_name);
        holder.solution_time.setText(list.get(position).solution_time);
        holder.solution_status.setText(list.get(position).solution_status);
        holder.execution_time.setText(list.get(position).solution_execution_time);
        holder.usage_memory.setText(list.get(position).usage_memory);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        holder.sub_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN ) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    problem_statement.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            String problem_link = list.get(position).problem_link;
                            if(problem_link != null) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(problem_link));
                                mContext.startActivity(intent);
                            }
                            else {
                                Toast.makeText(mContext, "Seems like this is private problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    source_code.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            String solution_link = list.get(position).solution_link;
                            Log.d("codecode", solution_link);
                            String tmp = solution_link;
                            if(solution_link != null) {
                                Intent intent = new Intent(mContext, sub_code_view.class);
                                intent.putExtra("link", solution_link);
                                intent.putExtra("problem_name", list.get(position).problem_name);
                                intent.putExtra("handle", list.get(position).getHandle());
                                intent.putExtra("judge", list.get(position).getJudge());
                                mContext.startActivity(intent);
                            }
                            else {
                                Toast.makeText(mContext, "Seems like this is on going contest problem! Try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        bottom_sheet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sub_recycler_viewHolder extends RecyclerView.ViewHolder{
        TextView problem_name;
        TextView solution_time;
        TextView solution_status;
        TextView execution_time;
        TextView usage_memory;
        ImageView image_judge;
        CardView sub_card_view;

        public sub_recycler_viewHolder(View itemView) {
            super(itemView);

            problem_name = itemView.findViewById(R.id.problem_name);
            solution_status = itemView.findViewById(R.id.solution_status);
            solution_time = itemView.findViewById(R.id.solution_time);
            execution_time = itemView.findViewById(R.id.ex_time);
            usage_memory = itemView.findViewById(R.id.usage_memory);
            image_judge = itemView.findViewById(R.id.judge_icon);
            sub_card_view = itemView.findViewById(R.id.sub_card_view);

        }
    }
}
