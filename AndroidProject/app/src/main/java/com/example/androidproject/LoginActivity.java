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
import com.example.androidproject.DTO.ResponseLogin;
import com.example.androidproject.DTO.UserDTO;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editId, editPW;
    TextView tvFind, tvSignup, errId, errPW;
    Button btnLogin;

    String strId, strPW;

    SharedPreferences auto;

    String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인");

        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        strId = auto.getString("inputId", null);
        strPW = auto.getString("inputPW",null);

        //null이 아니면 자동로그인
        if(strId !=null && strPW!=null){
            UserDTO user = getData();
            Call<ResponseLogin> call = RetrofitHelper.getApiService().login(user);
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if(response.isSuccessful()){
                        int result = response.code();
                        if(result == 200) {
                            Toast.makeText(LoginActivity.this, "자동 로그인 성공", Toast.LENGTH_SHORT).show();
                            Log.e("getName", response.body().toString());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(result == 204){
                            errPW.setVisibility(View.VISIBLE);
                            errPW.setText("아이디나 비밀번호가 일치하지 않습니다.");
                        }
                    }
                }


                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Log.e("err","통신 안됨: "+t.getMessage());
                }
            });
        }
        else if(strId == null && strPW == null){

        }

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findIdPW:
                Intent intent1 = new Intent(getApplicationContext(), FindPWDActivity.class);
                startActivity(intent1);
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

                    UserDTO user = getData();
                    Call<ResponseLogin> call = RetrofitHelper.getApiService().login(user);
                    call.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            if(response.isSuccessful()){
                                int result = response.code();
                                    if(result == 200) {
                                        Toast.makeText(LoginActivity.this, "자동 로그인 등록", Toast.LENGTH_SHORT).show();
                                        
                                        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor autoLogin = auto.edit();
                                        autoLogin.putString("inputId", strId);
                                        autoLogin.putString("inputPW", strPW);
                                        autoLogin.putString("inputName", response.body().getName());
                                        autoLogin.putString("inputBirth",response.body().getBirth());
                                        autoLogin.putString("inputContents", response.body().getStatus_msg());

                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.klogo2);

                                        bm.compress(Bitmap.CompressFormat.PNG, 40, baos);

                                        byte[] bImage = baos.toByteArray();
                                        String base64 = Base64.encodeToString(bImage,0);

                                        autoLogin.putString("inputBase64", base64);
                                        Log.e("getName", response.body().toString());
                                        autoLogin.commit();
                                        
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(result == 204){
                                        errPW.setVisibility(View.VISIBLE);
                                        errPW.setText("아이디나 비밀번호가 일치하지 않습니다.");
                                    }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Log.e("err","통신 안됨");
                        }
                    });
                }
                break;
        }
    }
    private UserDTO getData() {
        UserDTO data = new UserDTO(strId, strPW);
        return data;
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