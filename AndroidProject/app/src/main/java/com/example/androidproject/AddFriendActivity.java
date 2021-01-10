package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.API.RetrofitHelper;
import com.example.androidproject.DTO.AddFriend;
import com.example.androidproject.DTO.ResponseLogin;
import com.example.androidproject.DTO.UserDTO;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends AppCompatActivity {

    EditText editId;
    Button btnAdd;
    TextView textView;

    SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        editId = findViewById(R.id.editId);
        btnAdd = findViewById(R.id.addBtn);
        textView = findViewById(R.id.textView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editId.getText().toString().equals("")){
                    textView.setText("친구 추가할 email을 입력해주세요");
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    AddFriend user = new AddFriend(auto.getString("inputId", ""), editId.getText().toString());
                    Call<AddFriend> call = RetrofitHelper.getApiService().friend_add(user);
                    call.enqueue(new Callback<AddFriend>() {
                        @Override
                        public void onResponse(Call<AddFriend> call, Response<AddFriend> response) {
                            if (response.isSuccessful()) {
                                int result = response.code();
                                if (result == 200) {
                                    textView.setText("친구 추가가 완료되었습니다.");
                                    textView.setVisibility(View.VISIBLE);
                                } else if (result == 204) {
                                    textView.setText("존재하지 않는 사용자입니다.");
                                    textView.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddFriend> call, Throwable t) {
                            Log.e("err", "통신 안됨");
                        }
                    });
                }
            }
        });

    }
}