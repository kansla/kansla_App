package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfomationActivity extends AppCompatActivity {

    Button btnModify;
    TextView tvName, tvContents, tvEmail, tvPW, tvBirth;

    SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        setTitle("정보");

        init();

        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        tvName.setText(auto.getString("inputName","null"));
        tvContents.setText(auto.getString("inputContents","null"));
        tvEmail.setText(auto.getString("inputId","null"));
        tvBirth.setText(auto.getString("inputBirth","null"));

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoModifyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        btnModify = findViewById(R.id.btnModify);
        tvName = findViewById(R.id.name);
        tvContents = findViewById(R.id.contents);
        tvEmail = findViewById(R.id.email);
        tvPW = findViewById(R.id.password);
        tvBirth = findViewById(R.id.birth);
    }
}