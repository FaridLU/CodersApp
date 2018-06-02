package com.example.farid.codersappdemo;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.farid.codersappdemo.Adapter.UniversityListAdapter;
import com.example.farid.codersappdemo.Model.UniversityModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class UniversityListFragment extends Fragment {

    List<UniversityModel> universityList = new ArrayList<>();

    public UniversityListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.university_list_fragment, container, false);

        //Extra
        final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");
        dialog.setMessage("Please wait.");
        dialog.show();
        //Extra End

        RecyclerView universityListView = view.findViewById(R.id.university_list);
        final UniversityListAdapter listAdapter = new UniversityListAdapter(universityList, getContext(), getFragmentManager());


        universityListView.setLayoutManager(new VegaLayoutManager());
        universityListView.setAdapter(listAdapter);
        universityListView.smoothScrollToPosition(0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("universityList");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    universityList.clear();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        UniversityModel model = childSnapshot.getValue(UniversityModel.class);
                        if (model.getUniversityName() != null) {
                            universityList.add(model);
                            listAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
