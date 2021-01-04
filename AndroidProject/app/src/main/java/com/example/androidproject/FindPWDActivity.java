package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidproject.sendEmail.SendMail;

public class FindPWDActivity extends AppCompatActivity {

    static EditText editId;
    Button btnFind;
    static TextView textView;

    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_p_w_d);

        editId = findViewById(R.id.editId);
        btnFind = findViewById(R.id.findBtn);
        textView = findViewById(R.id.textView);
        pw = "asdf1234";

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail(){
        String email = editId.getText().toString().trim();
        String subject = "[känsla] 비밀번호 찾기 메일".trim();
        String message = "비밀번호를 잊어버리셨군요? 괜찮아요! 지금 알려드릴게요!!\n"+email+"님의 비밀번호는 ["
                +pw+"] 입니다. \n잃어버리지 않도록 조심해 주세요!".trim();

        SendMail sm = new SendMail(this, email, subject, message);

        sm.execute();
    }

    public void successSend(){
        textView.setVisibility(View.VISIBLE);
        textView.setText(editId.getText().toString()+"로 메일 전송이 완료되었습니다. \n메일이 오지 않을시 메일 주소를 다시 학인해주세요.");
    }
}