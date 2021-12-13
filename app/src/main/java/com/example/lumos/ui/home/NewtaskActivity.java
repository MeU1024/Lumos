package com.example.lumos.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lumos.DBOpenHelper;
import com.example.lumos.MainActivity;
import com.example.lumos.R;
import com.example.lumos.ui.info.login.LoginActivity;

import java.util.Calendar;

public class NewtaskActivity extends AppCompatActivity {

    private EditText name_input,days_input,description_input;
    private String name,days,description,DateToday;
    private Button btn_lumos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_newtask);
        name_input = (EditText) findViewById(R.id.task_name_text);
        days_input = (EditText) findViewById(R.id.task_days_text);
        description_input = (EditText) findViewById(R.id.task_description_text);
        btn_lumos = (Button) findViewById(R.id.btn_lumos);




        init();
    }

    private void init(){
        btn_lumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name=name_input.getText().toString().trim();
                days=days_input.getText().toString().trim();
                description=description_input.getText().toString().trim();
                DateToday = getDateToday();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(NewtaskActivity.this,"Please Enter Habit Name!",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(days)){
                    Toast.makeText(NewtaskActivity.this,"Please Enter Number of Days!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(description)){
                    Toast.makeText(NewtaskActivity.this,"Please Enter Description!",Toast.LENGTH_SHORT).show();
                }else{
                    //TODO:DATA BASE
                    String loginUserName = null;
                    loginUserName = getUserName();
                    if(loginUserName != null){
                        DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(NewtaskActivity.this, loginUserName+".db", null, 1);
                        final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("name", name);
                        values.put("days", days);
                        values.put("des", description);
                        values.put("cday", DateToday);
                        values.put("lday", getYesterday());
                        values.put("sday", DateToday);
                        values.put("state", 0);
                        long msg = db.insert("habit", "try", values);
                        Log.d("Debug", String.valueOf(msg));
                        db.close();
                    }
                    else{
                        Toast.makeText(NewtaskActivity.this,"Please Login First!",Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if(true){
                        Toast.makeText(NewtaskActivity.this,"A new star was added!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewtaskActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        return;
                    }
                }
            }
        });
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

    private String getYesterday(){

        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = (calendar.get(Calendar.DAY_OF_MONTH))-1;

        String DateToday = year+"-"+month+"-"+day;
        return DateToday;
    }
}

