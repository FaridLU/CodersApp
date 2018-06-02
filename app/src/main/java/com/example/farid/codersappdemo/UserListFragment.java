package com.example.farid.codersappdemo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farid.codersappdemo.Adapter.UserListAdapter;
import com.example.farid.codersappdemo.Model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    List<UserModel> userList = new ArrayList<>();

    private String universityKey;


    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_list_fragment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            universityKey = bundle.getString("UNIVERSITY_KEY");

            RecyclerView userListView = view.findViewById(R.id.user_list);
            final UserListAdapter listAdapter = new UserListAdapter(userList, getContext(), universityKey);


            userListView.setLayoutManager(new VegaLayoutManager());
            userListView.setAdapter(listAdapter);
            userListView.smoothScrollToPosition(0);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("universityWiseUserList").child(universityKey);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userList.clear();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            UserModel model = childSnapshot.getValue(UserModel.class);
                            userList.add(model);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return view;
    }

}
