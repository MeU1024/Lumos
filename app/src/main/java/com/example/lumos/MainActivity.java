package com.example.lumos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lumos.ui.collection.ShowActivity;
import com.example.lumos.ui.home.NewtaskActivity;
import com.example.lumos.ui.info.HelpActivity;
import com.example.lumos.ui.info.register.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lumos.databinding.ActivityMainBinding;

import com.example.lumos.ui.info.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,  R.id.navigation_review,R.id.navigation_collection,R.id.navigation_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

    public void userPageClick(View v){
        Intent intent = null;
        switch (v.getId()){
//            case R.id.info_anchievement:
//                intent = new Intent(MainActivity.this, AnchievementActivity.class);
//                break;
//            case R.id.info_statistics:
//                intent = new Intent(MainActivity.this, StatisticsActivity.class);
//                break;
            case R.id.info_help:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                break;
//            case R.id.info_share:
//                intent = new Intent(MainActivity.this,ShareActivity.class);
//                break;
//            case R.id.info_about:
//                intent = new Intent(MainActivity.this, AboutActivity.class);
//                break;
            case R.id.info_login:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                break;
            case R.id.info_register:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                break;
            case R.id.info_exit:
                SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();
                intent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.finish();
                break;
            case R.id.home_newtask:
                intent = new Intent(MainActivity.this, NewtaskActivity.class);
                break;

//            case R.id.collection_up:
//                intent = new Intent (MainActivity.this, ShowActivity.class);
//                overridePendingTransition(R.anim.in_from_bottom, R.anim.out_to_top);
//                break;

        }
        if(intent != null ) {
            startActivity(intent);
        }
    }

    public void starClick(View v){

        switch (v.getId()){

            case R.id.Today_star1:
                ChangeStar();
                break;

        }


    }

    public void ChangeStar(){
        if(true){

        }
    }

}