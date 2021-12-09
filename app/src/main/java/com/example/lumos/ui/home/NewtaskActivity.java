package com.example.lumos.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lumos.MainActivity;
import com.example.lumos.R;
import com.example.lumos.ui.info.login.LoginActivity;

public class NewtaskActivity extends AppCompatActivity {

    private EditText name_input,days_input,description_input;
    private String name,days,description;
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

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(NewtaskActivity.this,"Please Enter Habit Name!",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(days)){
                    Toast.makeText(NewtaskActivity.this,"Please Enter Number of Days!",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //TODO:DATA BASE

                    if(true){
                        Toast.makeText(NewtaskActivity.this,"A new star was added!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewtaskActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return;
                    }
                }
            }
        });
    }
}