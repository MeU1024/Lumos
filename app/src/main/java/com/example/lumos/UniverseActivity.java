package com.example.lumos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.example.lumos.ui.info.login.LoginActivity;
import com.example.lumos.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UniverseActivity extends AppCompatActivity {

    private TextView useday,starscount,max_habit_tv,uni_tv;
    Context context;
    private ConstraintLayout uni_ConstraintLayout;
    private int max_count=0;
    private String max_habit_name = null;
    private Button btn_sharing;
    View cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_universe);
        useday = (TextView) findViewById(R.id.text_useday);
        starscount = (TextView) findViewById(R.id.text_stars);
        max_habit_tv = (TextView) findViewById(R.id.text_habit);
        uni_tv = (TextView)  findViewById(R.id.panorama);
        btn_sharing = (Button) findViewById(R.id.btn_sharing);
        uni_ConstraintLayout = (ConstraintLayout) findViewById(R.id.uni_ConstraintLayout);
        cv = getWindow().getDecorView();
        context = UniverseActivity.this;
        //uni_ConstraintLayout = (ConstraintLayout) findViewById(R.id.help_ConstraintLayout);
        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void init() throws ParseException {
        //打开数据库，获取当前选择的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String loginUserName = null;
        loginUserName = getUserName();
        DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(UniverseActivity.this, loginUserName+".db", null, 1);
        final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

//        String sql_done = "select count(*) from record ";
//        Cursor cursor_done = db.rawQuery(sql_done, null);
//        cursor_done.moveToFirst();
//        long done_count = cursor_done.getLong(0);
//        cursor_done.close();
        int done_count = 0;
        String sql_done = "select * from record WHERE rowid = 1 ";
        Cursor cursor_done = db.rawQuery(sql_done, null);
        while(cursor_done.moveToNext()){

            String str_today = cursor_done.getString(cursor_done.getColumnIndex("today"));

            Date first_day = simpleDateFormat.parse(str_today);
            Date today_date = simpleDateFormat.parse(getDateToday());

            Calendar c1=Calendar.getInstance();
            c1.setTime(first_day);//把获取的入住时间年月日放入Calendar中
            Calendar c2=Calendar.getInstance();
            c2.setTime(today_date);//把获取的退房时间年月日放入Calendar中

            done_count = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) + 1;

        }

        cursor_done.close();

        useday.setText("You have used Lumos for "+ Long.toString(done_count)+" days!");

        String sql_stars = "select count(*) from habit where state = 1 ";
        Cursor cursor_stars = db.rawQuery(sql_stars, null);
        cursor_stars.moveToFirst();
        long done_stars = cursor_stars.getLong(0);
        cursor_stars.close();

        starscount.setText("Totally, "+ done_stars +" stars are collected.");

        String sql_max_habit = "select * from habit ";
        Cursor cursor_max_habit = db.rawQuery(sql_max_habit,null);
        //Cursor cursor_select_today_habit = db.query("habit", null, "state=0", null, null, null, null);
        while(cursor_max_habit.moveToNext()){
            //Integer id = cursor_select_today_habit.getInt(cursor_select_today_habit.getColumnIndex("rowid"));
            String name = cursor_max_habit.getString(cursor_max_habit.getColumnIndex("name"));
            String des = cursor_max_habit.getString(cursor_max_habit.getColumnIndex("des"));
            Integer days = cursor_max_habit.getInt(cursor_max_habit.getColumnIndex("days"));
            Integer state = cursor_max_habit.getInt(cursor_max_habit.getColumnIndex("state"));
            String str_cday = cursor_max_habit.getString(cursor_max_habit.getColumnIndex("cday"));
            String str_lday = cursor_max_habit.getString(cursor_max_habit.getColumnIndex("lday"));
            String str_sday = cursor_max_habit.getString(cursor_max_habit.getColumnIndex("sday"));

            Date cday = simpleDateFormat.parse(str_cday);
            Date lday = simpleDateFormat.parse(str_lday);
            Date sday = simpleDateFormat.parse(str_sday);

            Calendar c1=Calendar.getInstance();
            c1.setTime(sday);//把获取的入住时间年月日放入Calendar中
            Calendar c2=Calendar.getInstance();
            c2.setTime(lday);//把获取的退房时间年月日放入Calendar中

            int progress = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) + 1;
            if(progress >= max_count){
                max_habit_name = name;
                max_count = progress;
            }
        }

        cursor_max_habit.close();

        max_habit_tv.setText("The star existing for longest time is '" + max_habit_name +"' , lasting for "+max_count +" days in total." );

        db.close();

        uni_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String path = ImageUtils.viewSaveToImage(cv,"mypic");
                uni_tv.setVisibility(View.INVISIBLE);
                Bitmap bmp = ImageUtils.myShot(UniverseActivity.this);
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/");
                if(!ImageUtils.isGrantExternalRW(UniverseActivity.this)){
                    return;
                }
                try {

                    //ImageUtils.saveToSD(bmp,context.getFilesDir().getPath().toString(),"/mypic.png");
                    ImageUtils.saveToSD(bmp,cachePath.toString(),"/mypic.png");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("libzone.cn exception:", String.valueOf(e));
                }
                //Toast.makeText(UniverseActivity.this,context.getFilesDir().getPath().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UniverseActivity.this,"Save Sucessfully!", Toast.LENGTH_SHORT).show();

//                File root = Environment.getExternalStorageDirectory();
//                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
//                try
//                {
//                    cachePath.createNewFile();
//                    FileOutputStream ostream = new FileOutputStream(cachePath);
//                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//                    ostream.flush();
//                    ostream.close();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
            }
        });

        return;
    }

    private String getUserName(){
        String loginUserName = null;
        SharedPreferences sp= getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        loginUserName = sp.getString("loginUserName" , null);
        return loginUserName;

    }

    private String getDateToday(){

        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String DateToday = year+"-"+month+"-"+day;
        return DateToday;
    }
}