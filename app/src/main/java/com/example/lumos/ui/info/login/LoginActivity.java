package com.example.lumos.ui.info.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lumos.MainActivity;
import com.example.lumos.ui.info.login.LoginActivity;
import com.example.lumos.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email_input,password_input;
    private String email,password,spPsw;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_login);
        email_input = (EditText) findViewById(R.id.username);
        password_input = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);
        password_input.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        init();
    }

    private void init() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=email_input.getText().toString().trim();
                password=password_input.getText().toString().trim();
                String md5Psw=password;
                spPsw=readPsw(email);
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Please enter your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please nter your password!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(spPsw)){

                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    saveLoginStatus(true, email);
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    return;
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(LoginActivity.this, "The Email or password is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this, "Please Register first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /** *从SharedPreferences中根据用户名读取密码 */
    private String readPsw(String userName){

        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName , "");
    }
    /** *保存登陆状态和登陆用户名到SharedPreferences中 */
    private void saveLoginStatus(boolean status,String userName){

        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){

                email_input.setText(userName);
                email_input.setSelection(userName.length());
            }
        }
    }






}