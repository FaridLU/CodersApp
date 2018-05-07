package com.example.farid.codersappdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class user_profile extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tv = findViewById(R.id.tv);

        String ans = getIntent().getStringExtra("name");
        tv.setText(ans);
    }
}
