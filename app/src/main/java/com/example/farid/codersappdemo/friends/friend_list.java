package com.example.farid.codersappdemo.friends;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farid.codersappdemo.R;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class friend_list extends Fragment implements View.OnClickListener{

    CircleImageView circleImageView;
    ImageView friend_img;
    TextView friend_name_tv, getFriend_institute_tv;

    List<FriendActivity> Friend_List = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_friend_list, container, false);
        RecyclerView recyclerViewFriend = view.findViewById(R.id.recyclerview_friend);
        friend_RecyclerAdapter friend_adapter = new friend_RecyclerAdapter(Friend_List, getActivity());

        Friend_List.add(new FriendActivity("Ashraful Haque Chy", "Leading University, Sylhet", "ASH_9353", "the_mover", "the_mover", R.drawable.man));
        Friend_List.add(new FriendActivity("Araf Al Jami", "Leading University, Sylhet", "CLown1331", "clown1331", "clown1331", R.drawable.man));
        Friend_List.add(new FriendActivity("Raihan Hussain Zinuk", "Leading University, Sylhet", "DarkknightRHZ", "darkknightrhz", "darkknightrhz", R.drawable.man));
        Friend_List.add(new FriendActivity("Mohammad Jamil", "Leading University, Sylhet", "mohammadjamil994", "jamil993", "jamil993", R.drawable.man));
        Friend_List.add(new FriendActivity("Tareq Aziz khan", "Leading University, Sylhet", "the_badcoder", "the_badcoder", "the_badcoder", R.drawable.man));
        Friend_List.add(new FriendActivity("Mahfuz Hasan", "Leading University, Sylhet", "mahfuz.lu", "m4hfuz", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Anik Roy", "Leading University, Sylhet", "roy", "anik_roy", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Imranuzzaman Imran", "Leading University, Sylhet", "IzZaman", "izzaman", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Shamim Ahmed", "Leading University, Sylhet", "shamim03", "shamim003", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Ryan Shojib", "Leading University, Sylhet", "factor", "factor", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Habibur Rahman", "Leading University, Sylhet", "habibmax95", "factor", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Rasel Ahmed", "Leading University, Sylhet", "RaselAhmed", "elubilu", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Mashud Zaman", "Leading University, Sylhet", "MaSh-uD", "mash_ud", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Kollol Chakroborty", "Leading University, Sylhet", "cyanomax", "cyanomax", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Sayed Al Mahdi", "Leading University, Sylhet", "the_badcoder", "sayedalmahdi", "Fake_Death", R.drawable.man));
        Friend_List.add(new FriendActivity("Farid ul Islam Chy", "Leading University, Sylhet", "_FariD_", "fake_death", "Fake_Death", R.drawable.man));


        recyclerViewFriend.setLayoutManager(new VegaLayoutManager());
        recyclerViewFriend.setAdapter(friend_adapter);
        recyclerViewFriend.smoothScrollToPosition(0);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        circleImageView = getView().findViewById(R.id.friend_image);
        friend_img = getView().findViewById(R.id.friend_image);
        friend_name_tv = getView().findViewById(R.id.friend_name);
        getFriend_institute_tv = getView().findViewById(R.id.friend_institute);
    }

    @Override
    public void onClick(View view) {

    }

    public class FriendActivity {

        public String friend_name, friend_institue, codeforcesID, codechefID, uvaID;

        public FriendActivity(String friend_name, String friend_institue, String codeforcesID, String codechefID, String uvaID, int friend_image) {
            this.friend_name = friend_name;
            this.friend_institue = friend_institue;
            this.codeforcesID = codeforcesID;
            this.codechefID = codechefID;
            this.friend_image = friend_image;
            this.uvaID = uvaID;
        }

        public void setUvaID(String uvaID) {
            this.uvaID = uvaID;
        }

        public String getUvaID() {

            return uvaID;
        }

        public void setCodeforcesID(String codeforcesID) {
            this.codeforcesID = codeforcesID;
        }

        public void setCodechefID(String codechefID) {
            this.codechefID = codechefID;
        }

        public String getCodeforcesID() {

            return codeforcesID;
        }

        public String getCodechefID() {
            return codechefID;
        }

        public int friend_image;

        public String getFriend_name() {
            return friend_name;
        }

        public void setFriend_name(String friend_name) {
            this.friend_name = friend_name;
        }

        public void setFriend_institue(String friend_institue) {
            this.friend_institue = friend_institue;
        }

        public void setFriend_image(int friend_image) {
            this.friend_image = friend_image;
        }

        public String getFriend_institue() {
            return friend_institue;
        }

        public int getFriend_image() {
            return friend_image;
        }
    }
}
