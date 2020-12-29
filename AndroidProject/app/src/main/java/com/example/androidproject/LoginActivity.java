package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editId, editPW;
    TextView tvFind, tvSignup, errId, errPW;
    Button btnLogin;

    String strId, strPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인");

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findIdPW:
                break;
            case R.id.signup:
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:
                strId = editId.getText().toString();
                strPW = editPW.getText().toString();

                if(strId.equals("") || strPW.equals("")) {
                    if (strId.equals("")) {
                        errId.setVisibility(View.VISIBLE);
                    } else
                        errId.setVisibility(View.GONE);
                    if (strPW.equals("")) {
                        errPW.setVisibility(View.VISIBLE);
                    } else
                        errPW.setVisibility(View.GONE);
                }
                else{
                    errPW.setVisibility(View.GONE);
                    errId.setVisibility(View.GONE);
                    // 로그인 확인하기
                    if(true){
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent2);
                    }
                }
                break;
        }
    }

    void init(){
        editId = findViewById(R.id.editId);
        editPW = findViewById(R.id.editPW);
        tvFind = findViewById(R.id.findIdPW);
        tvSignup = findViewById(R.id.signup);
        btnLogin = findViewById(R.id.loginBtn);
        errId = findViewById(R.id.errId);
        errPW = findViewById(R.id.errPW);

        btnLogin.setOnClickListener(this);
        tvFind.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }
}