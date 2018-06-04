package com.example.farid.codersappdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.farid.codersappdemo.friends.friend_list;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard_activity extends Fragment implements View.OnClickListener {

    CardView contests_card, friends_card, ranking_card, calculator_card, my_profile_card;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tagtag", "dashboard_activity");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View v = getView();

        contests_card = v.findViewById(R.id.contests_card);
        friends_card = v.findViewById(R.id.friends_card);
        ranking_card = v.findViewById(R.id.ranking_card);
        calculator_card = v.findViewById(R.id.calculator_card);
        my_profile_card = v.findViewById(R.id.my_profile_card);
        //Toast.makeText(getActivity(), FirebaseAuth.getInstance().getCurrentUser().getUid() + "**", Toast.LENGTH_SHORT).show();

        contests_card.setOnClickListener(this);
        friends_card.setOnClickListener(this);
        ranking_card.setOnClickListener(this);
        calculator_card.setOnClickListener(this);
        my_profile_card.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.contests_card: intent = new Intent(getActivity(), MainActivity.class); intent.putExtra("type","contests"); startActivity(intent); break;
            case R.id.friends_card: intent = new Intent(getActivity(), MainActivity.class); intent.putExtra("type","friends"); startActivity(intent);break;
            case R.id.ranking_card: intent = new Intent(getActivity(), MainActivity.class); intent.putExtra("type","ranking"); startActivity(intent);break;
            case R.id.calculator_card: intent = new Intent(getActivity(), MainActivity.class); intent.putExtra("type","calculator"); startActivity(intent);break;
            case R.id.my_profile_card: {
                Intent intent2 = new Intent(getActivity(), user_profile_loading.class);
                intent2.putExtra("type", 3);
                intent2.putExtra("USER_KEY", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(intent2);
                break;
            }
        }

    }
}
