package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfomationActivity extends AppCompatActivity {

    Button btnModify;
    TextView tvName, tvContents, tvEmail, tvPW, tvBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        init();

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

        tvName.setText("나다 문예원0");
        tvContents.setText("항상 배고픔");
        tvEmail.setText("gongju@gsm.hs.kr");
        tvPW.setText("****");
        tvBirth.setText("2003-04-10");
    }
}