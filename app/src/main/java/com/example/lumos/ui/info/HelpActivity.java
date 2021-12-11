package com.example.lumos.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lumos.MainActivity;
import com.example.lumos.R;
import com.example.lumos.ui.info.login.LoginActivity;

public class HelpActivity extends AppCompatActivity {

    private ConstraintLayout help_ConstraintLayout;
    private TextView tv_q1,tv_q2,tv_a1,tv_a2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_help);
        help_ConstraintLayout = (ConstraintLayout) findViewById(R.id.help_ConstraintLayout);
        tv_q1 = (TextView) findViewById(R.id.text_question1);
        tv_a1 = (TextView) findViewById(R.id.text_answer1);
        tv_q2 = (TextView) findViewById(R.id.text_question2);
        tv_a2 = (TextView) findViewById(R.id.text_answer2);
        init();
    }

    private void init() {

        help_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_q1.setVisibility(View.INVISIBLE);
                tv_a1.setVisibility(View.INVISIBLE);
                tv_q2.setVisibility(View.VISIBLE);
                tv_a2.setVisibility(View.VISIBLE);

            }
        });
    }


}