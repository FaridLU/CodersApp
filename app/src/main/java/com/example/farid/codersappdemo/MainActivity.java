package com.example.farid.codersappdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.farid.codersappdemo.contest.main_contest;
import com.example.farid.codersappdemo.friends.friend_list;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView contests_card, friends_card, ranking_card, calculator_card, settings_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        contests_card = findViewById(R.id.contests_card);
        friends_card = findViewById(R.id.friends_card);
        ranking_card = findViewById(R.id.ranking_card);
        calculator_card = findViewById(R.id.calculator_card);
        settings_card = findViewById(R.id.settings_card);

        contests_card.setOnClickListener(this);
        friends_card.setOnClickListener(this);
        ranking_card.setOnClickListener(this);
        calculator_card.setOnClickListener(this);
        settings_card.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Close app")
                .setMessage("Are you sure you want to close this app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finishAffinity();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.contests_card: intent = new Intent(this, loading_activity.class); startActivity(intent); break;
            case R.id.friends_card: intent = new Intent(this, friend_list.class); startActivity(intent);break;
            case R.id.ranking_card: intent = new Intent(this, loading_activity.class);startActivity(intent);break;
            case R.id.calculator_card: intent = new Intent(this, loading_activity.class);startActivity(intent);break;
            case R.id.settings_card: intent = new Intent(this, loading_activity.class);startActivity(intent);break;
        }

    }
}
