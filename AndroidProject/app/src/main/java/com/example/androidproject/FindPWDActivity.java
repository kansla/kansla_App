package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.API.RetrofitHelper;
import com.example.androidproject.DTO.UserDTO;
import com.example.androidproject.sendEmail.SendMail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPWDActivity extends AppCompatActivity {

    static EditText editId;
    Button btnFind;
    static TextView textView;

    static String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_p_w_d);

        editId = findViewById(R.id.editId);
        btnFind = findViewById(R.id.findBtn);
        textView = findViewById(R.id.textView);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDTO user = new UserDTO(editId.getText().toString());
                Call<UserDTO> call = RetrofitHelper.getApiService().find_password(user);
                call.enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        if(response.isSuccessful()){
                            pw = response.body().getPwd();
                            Log.e("pwd", pw);

                            sendEmail();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        Log.e("실패", t.getMessage());
                    }
                });
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