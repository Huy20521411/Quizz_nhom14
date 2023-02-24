package com.example.quizz_nhom14.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.fragment.ChangePasswordFragment;
import com.example.quizz_nhom14.fragment.GV_Fragment;
import com.example.quizz_nhom14.fragment.ProfileFragment;
import com.example.quizz_nhom14.fragment.ProgressFragment;
import com.example.quizz_nhom14.fragment.QuizFragment;
import com.example.quizz_nhom14.object.User;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_QUIZ=0;
    private static final int FRAGMENT_PROGRESS=1;
    private static final int FRAGMENT_PROFILE =2;
    private static final int FRAGMENT_CHANGEPASS =3;
    private static final int FRAGMENT_GV =4;
    private int mCurrentFragment=FRAGMENT_QUIZ;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    FragmentManager fm=getSupportFragmentManager();
    User user;
    TextView tv_fullname,tv_email;
    public User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        user= (User) intent.getSerializableExtra("user");
        if(user.isClasssify()){
            setContentView(R.layout.activity_main_gv);
            navigationView=findViewById(R.id.nav_view_gv);
        }
        else{
            setContentView(R.layout.activity_main_sv);
            navigationView=findViewById(R.id.nav_view_sv);
        }
        mDrawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        View headerView = navigationView.getHeaderView(0);
        tv_email=headerView.findViewById(R.id.tv_email);
        tv_fullname=headerView.findViewById(R.id.usernamenav);
        tv_fullname.setText(user.getFullname());
        tv_email.setText(user.getEmail());
        if(user.isClasssify()) toolbar.setTitle("MY QUIZS");
        else toolbar.setTitle("QUIZS");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar
                ,R.string.nav_draw_start,R.string.nav_drawe_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if(user.isClasssify()){
            replaceFragment(new GV_Fragment());
            navigationView.getMenu().findItem(R.id.nav_gv).setChecked(true);
        }
        else{
            replaceFragment(new QuizFragment());
            navigationView.getMenu().findItem(R.id.nav_quiz).setChecked(true);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_quiz){
            if(mCurrentFragment!=FRAGMENT_QUIZ){
                toolbar.setTitle("QUIZ");
                replaceFragment(new QuizFragment());
                mCurrentFragment =FRAGMENT_QUIZ;
            }
        }else if(id==R.id.nav_progress){
            if(mCurrentFragment!=FRAGMENT_PROGRESS){
                toolbar.setTitle("PROGRESS");
                replaceFragment(new ProgressFragment());
                mCurrentFragment =FRAGMENT_PROGRESS;
            }
        }else if(id==R.id.nav_gv){
            if(mCurrentFragment!=FRAGMENT_GV){
                toolbar.setTitle("MY QUIZS");
                replaceFragment(new GV_Fragment());
                mCurrentFragment =FRAGMENT_GV;
            }
        }else if(id==R.id.nav_profile){
            toolbar.setTitle("MY PROFILE");
            if(mCurrentFragment!=FRAGMENT_PROFILE){
                replaceFragment(new ProfileFragment());
                mCurrentFragment =FRAGMENT_PROFILE;
            }
        }else if(id==R.id.nav_changepassword){
            if(mCurrentFragment!=FRAGMENT_CHANGEPASS){
                toolbar.setTitle("CHANGE PASSWORD");
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment =FRAGMENT_CHANGEPASS;
            }
        }
        if(user.isClasssify()){
            navigationView.getMenu().findItem(R.id.nav_gv).setChecked(false);
        }
        else{
            navigationView.getMenu().findItem(R.id.nav_quiz).setChecked(false);
            navigationView.getMenu().findItem(R.id.nav_progress).setChecked(false);
        }
        navigationView.getMenu().findItem(R.id.nav_profile).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_changepassword).setChecked(false);
        navigationView.getMenu().findItem(id).setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            Dialog dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_confirm_logout);
            TextView huy,ok,tit;
            tit=dialog.findViewById(R.id.dialog_tit);
            tit.setText("You want to exit ?");
            huy=dialog.findViewById(R.id.dialog_huy);
            ok=dialog.findViewById(R.id.dialog_ok);
            dialog.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    MainActivity.super.onBackPressed();
                }
            });
            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
}
