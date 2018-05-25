package com.example.farid.codersappdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.example.farid.codersappdemo.contest.main_contest;
import com.example.farid.codersappdemo.friends.friend_list;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;
    private int prev_position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();



        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("DashBoard",R.drawable.material_background));
        menuItems.add(new MenuItem("Contests",R.drawable.material_background));
        menuItems.add(new MenuItem("Friends",R.drawable.material_background));
        menuItems.add(new MenuItem("Ranking",R.drawable.material_background));
        menuItems.add(new MenuItem("Calculator",R.drawable.material_background));
        menuItems.add(new MenuItem("My Profile",R.drawable.material_background));
        menuItems.add(new MenuItem("Exit",R.drawable.material_background));

        sNavigationDrawer.setAppbarTitleTV("Dashboard");
        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  dashboard_activity.class;

        try {
            Log.d("tagtag", "mainactivity");

            fragment = (Fragment) fragmentClass.newInstance();
            Log.d("tagtag", "initialization complete");
        } catch (Exception e) {
            System.out.println( "catch e hamaise");
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }




        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        prev_position= 0;
                        fragmentClass = dashboard_activity.class;
                        break;
                    }
                    case 1:{
                        prev_position= 1;
                        fragmentClass = loading_activity.class;
                        break;
                    }
                    case 2:{
                        prev_position= 2;
                        System.out.println("friend paisi");
                        fragmentClass = friend_list.class;
                        break;
                    }
                    case 6:{
                        onBackPressed();
                        break;
                    }

                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                            System.out.println("close howar bade try");
                        } catch (Exception e) {
                            System.out.println("close howar bade catch");
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });

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
                if (fragment != null) {
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if(prev_position == 0) sNavigationDrawer.setAppbarTitleTV("Dashboard");
                    else if(prev_position == 1) sNavigationDrawer.setAppbarTitleTV("Contests");
                    else if(prev_position == 2) sNavigationDrawer.setAppbarTitleTV("Friends");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                }
            }
        });
        builder.show();
    }


}
