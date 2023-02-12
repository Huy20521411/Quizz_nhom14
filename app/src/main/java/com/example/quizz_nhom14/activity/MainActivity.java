package com.example.quizz_nhom14.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.fragment.ProgressFragment;
import com.example.quizz_nhom14.fragment.QuizFragment;
import com.example.quizz_nhom14.object.User;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_QUIZ=0;
    private static final int FRAGMENT_PROGRESS=1;
    private static final int FRAGMENT_PROFILE =2;
    private static final int FRAGMENT_CHANGEPASS =3;
    private int mCurrentFragment=FRAGMENT_QUIZ;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    QuizFragment fquiz=new QuizFragment();
    ProgressFragment fprogress=new ProgressFragment();
    FragmentManager fm=getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=findViewById(R.id.drawer_layout);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("QUIZS");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar
                ,R.string.nav_draw_start,R.string.nav_drawe_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        fquiz=new QuizFragment();
//        fprogress=new ProgressFragment();

        replaceFragment(fquiz);
        navigationView.getMenu().findItem(R.id.nav_quiz).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_quiz){
            if(mCurrentFragment!=FRAGMENT_QUIZ){
                toolbar.setTitle("QUIZ");
                replaceFragment(fquiz);
                mCurrentFragment =FRAGMENT_QUIZ;
            }
        }else if(id==R.id.nav_progress){
            if(mCurrentFragment!=FRAGMENT_PROGRESS){
                toolbar.setTitle("PROGRESS");
                replaceFragment(fprogress);
                mCurrentFragment =FRAGMENT_PROGRESS;
            }
        }else if(id==R.id.nav_profile){
            toolbar.setTitle("MY PROFILE");
            if(mCurrentFragment!=FRAGMENT_QUIZ){
                replaceFragment(new QuizFragment());
                mCurrentFragment =FRAGMENT_QUIZ;
            }
        }else if(id==R.id.nav_changepassword){
            if(mCurrentFragment!=FRAGMENT_QUIZ){
                toolbar.setTitle("CHANGE PASSWORD");
                replaceFragment(new QuizFragment());
                mCurrentFragment =FRAGMENT_QUIZ;
            }
        }
        navigationView.getMenu().findItem(R.id.nav_quiz).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_progress).setChecked(false);
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
        }else super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate (backStateName, 0);
        if(!fragmentPopped){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(backStateName);
            transaction.replace(R.id.content_frame,fragment);
            transaction.commit();
        }
    }
}