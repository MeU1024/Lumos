package com.example.lumos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class UniverseActivity extends AppCompatActivity {

    private TextView useday,starscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_universe);
        useday = (TextView) findViewById(R.id.text_useday);
        starscount = (TextView) findViewById(R.id.text_stars);
        init();
    }
    public void init(){
        //打开数据库，获取当前选择的日期
        String loginUserName = null;
        loginUserName = getUserName();
        DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(UniverseActivity.this, loginUserName+".db", null, 1);
        final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

        String sql_done = "select count(*) from record ";
        Cursor cursor_done = db.rawQuery(sql_done, null);
        cursor_done.moveToFirst();
        long done_count = cursor_done.getLong(0);
        cursor_done.close();

        useday.setText("You have used Lumos for "+ Long.toString(done_count)+" days!");

        String sql_stars = "select count(*) from habit where state = 1 ";
        Cursor cursor_stars = db.rawQuery(sql_stars, null);
        cursor_stars.moveToFirst();
        long done_stars = cursor_stars.getLong(0);
        cursor_stars.close();

        starscount.setText("Totally, "+ done_stars +" stars are collected.");


    }

    private String getUserName(){
        String loginUserName = null;
        SharedPreferences sp= getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        loginUserName = sp.getString("loginUserName" , null);
        return loginUserName;

    }
}