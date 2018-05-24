package com.example.farid.codersappdemo.contest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farid.codersappdemo.MainActivity;
import com.example.farid.codersappdemo.R;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.SweetSheet;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Random;
import android.os.Handler;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.recyclerViewHolder> {

        Context mContest;
        List< ContestActivity > list;
        int present_contest = 1, upcomming_contest = 2, flg = 0;
        LinearLayout facebook, whatsapp, telegram, googleplus, email, messenger;
        RelativeLayout share_bottom_sheet;

        SweetSheet bottomSheetBehavior;
        AVLoadingIndicatorView avi;
        Button temp;


        public RecyclerAdapter(Context mContest, List<ContestActivity> list, LinearLayout facebook, LinearLayout whatsapp, LinearLayout telegram, LinearLayout googleplus, LinearLayout email, LinearLayout messenger, SweetSheet bottomSheetBehavior, RelativeLayout share_bottom_sheet) {
            this.mContest = mContest;
            this.list = list;
            this.facebook = facebook;
            this.whatsapp = whatsapp;
            this.telegram = telegram;
            this.messenger = messenger;
            this.googleplus = googleplus;
            this.email = email;
            this.bottomSheetBehavior = bottomSheetBehavior;
            this.share_bottom_sheet = share_bottom_sheet;
        }


        @Override
        public recyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;

            LayoutInflater inflater = LayoutInflater.from(mContest);
            view = inflater.inflate(R.layout.card_view_contest, parent, false);
            RelativeLayout tv = view.findViewById(R.id.contest_name_layout);

            avi=  view.findViewById(R.id.avi);

            if(viewType == 1) {
                avi.setIndicator("BallScaleMultipleIndicator");
                avi.show();
                tv.setBackgroundColor(Color.parseColor("#000000"));
            } else {
                avi.setVisibility(View.GONE);
                //int red = Color.parseColor("#FF4EA184");
                int red = Color.parseColor("#e4000000");
                tv.setBackgroundColor(red);
                //background_anim.stop();
            }
            return new recyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final recyclerViewHolder holder, final int position) {

            holder.tv_contest_name.setText(list.get(position).getContest_name());
            holder.tv_start_date.setText(list.get(position).getStart_date());
            holder.tv_start_information.setText(list.get(position).getStart_information());
            holder.tv_end_date.setText(list.get(position).getEnd_date());
            holder.tv_end_information.setText(list.get(position).getEnd_information());
            holder.img_contest.setImageResource(list.get(position).getContest_image());

            holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog mDialog = new Dialog(mContest);
                    if(bottomSheetBehavior.isShow()) bottomSheetBehavior.dismiss();

                    mDialog.getWindow().getAttributes().windowAnimations = R.style.SlideScale;

                    mDialog.setContentView(R.layout.custom_popup_window);
                    mDialog.show();

                    TextView title = mDialog.findViewById(R.id.tv_contest_title);
                    title.setText(list.get(position).getContest_name());
                    TextView txt_close = mDialog.findViewById(R.id.tv_close);
                    LinearLayout browse = mDialog.findViewById(R.id.browse);
                    LinearLayout addReminder = mDialog.findViewById(R.id.addreminder);
                    LinearLayout share = mDialog.findViewById(R.id.share_link);
                    ImageView icon = mDialog.findViewById(R.id.tv_judge_icon);

                    if(list.get(position).getJudge().toLowerCase() == "codechef") {
                        icon.setImageResource(R.drawable.codechef);
                    }

                    final TextView status = mDialog.findViewById(R.id.tv_status);
                    if(list.get(position).contest_type == 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    status.setText("Running.");
                                    Thread.sleep(500);
                                    status.setText("Running..");
                                    Thread.sleep(500);
                                    status.setText("Running...");
                                    Thread.sleep(500);
                                    status.setText("Running.....");
                                    Thread.sleep(500);
                                    status.setText("Running......");
                                    Thread.sleep(500);
                                    status.setText("Running.......");
                                    Thread.sleep(500);
                                    run();
                                } catch (Exception e) {
                                }
                            }
                        }).start();
                    } else {
                        status.setText("The Contest Is not started yet");
                    }
                    txt_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    browse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = list.get(position).getContest_link();
                            if(url != null) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                mContest.startActivity(intent);
                            }
                            else {
                                Toast.makeText(mContest, "Sorry.. No valid URL is there", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    addReminder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContest, "Sorry Reminder option is not added yet..", Toast.LENGTH_SHORT).show();
                        }
                    });

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContest, "Sorry Share option is not added yet..", Toast.LENGTH_SHORT).show();
                            //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    });
                    return false;
                }
            }) ;

            holder.card_view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.show();
                    bottomSheetBehavior.setBackgroundEffect(new BlurEffect(8));
                    Intent intent = new Intent("Pass");
                    intent.putExtra("position", position);
                    LocalBroadcastManager.getInstance(mContest).sendBroadcast(intent);

                }});
        }
        @Override
        public int getItemViewType(int position) {
            if(list.get(position).getContest_type() == 1) {
                return  1;
            }
            else return 2;
        }

        @Override
        public int getItemCount() {
            return list.size() == 0 ? 0 : list.size();
        }

        public class recyclerViewHolder extends RecyclerView.ViewHolder {

            TextView tv_start_date;
            TextView tv_start_information;
            TextView tv_end_date;
            TextView tv_end_information;
            TextView tv_contest_name;
            ImageView img_contest;
            CardView card_view;

            public recyclerViewHolder(View itemView) {
                super(itemView);

                tv_start_date = itemView.findViewById(R.id.start_date);
                tv_start_information = itemView.findViewById(R.id.start_information);
                tv_end_date = itemView.findViewById(R.id.end_date);
                tv_end_information = itemView.findViewById(R.id.end_information);
                tv_contest_name = itemView.findViewById(R.id.contest_name);
                img_contest = itemView.findViewById(R.id.img_contest);
                card_view = itemView.findViewById(R.id.card_view);

                /*View view = LayoutInflater.from(mContest).inflate(R.layout.bottom_sheet_share, null, false);
                temp = view.findViewById(R.id.temp);
                view.findViewById(R.id.temp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("tt", "class");
                        bottomSheetBehavior.dismiss();
                    }
                });*/
            }
        }
}
