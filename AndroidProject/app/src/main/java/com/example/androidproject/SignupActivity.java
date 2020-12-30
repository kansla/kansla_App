package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.API.RetrofitHelper;
import com.example.androidproject.DTO.UserDTO;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editId, editPW, editRePW, editName;
    TextView errId, errPW, errRePW, errBirth, tvBirth;
    Button btnSignup, btnCheck;

    String strId, strPW, strRePW, strBirth, strName;

    boolean isAbleId = false;

    int y=0, m=0, d=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("회원가입");

        init();

        btnSignup.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        tvBirth.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup:
                strId = editId.getText().toString();
                strPW = editPW.getText().toString();
                strRePW = editRePW.getText().toString();
                strBirth = tvBirth.getText().toString();
                strName = editName.getText().toString();

                if(strId.equals("") || strPW.equals("") || strRePW.equals("") || strBirth.equals("")){
                    if(strId.equals("")){
                        errId.setText("아이디를 비워둘 수 없습니다.");
                        errId.setVisibility(View.VISIBLE);
                    }else
                        errId.setVisibility(View.GONE);
                    if(strPW.equals("")){
                        errPW.setText("비밀번호를 비워둘 수 없습니다.");
                        errPW.setVisibility(View.VISIBLE);
                    }else
                        errPW.setVisibility(View.GONE);
                    if(strRePW.equals("")){
                        errRePW.setText("비밀번호를 재확인 해주세요");
                        errRePW.setVisibility(View.VISIBLE);
                    }else
                        errRePW.setVisibility(View.GONE);
                    if(strBirth.equals("")){
                        errBirth.setText("생년월일은 비워둘 수 없습니다.");
                        errBirth.setVisibility(View.VISIBLE);
                    }else
                        errBirth.setVisibility(View.GONE);
                }
                else{
                    errId.setVisibility(View.GONE);
                    errRePW.setVisibility(View.GONE);
                    errPW.setVisibility(View.GONE);
                    errBirth.setVisibility(View.GONE);
                    
                    // id 중복확인
                    if(!isAbleId){
                        errId.setText("중복확인을 해주세요");
                        errId.setVisibility(View.VISIBLE);
                    }
                    else
                        errId.setVisibility(View.GONE);
                    // 비밀번호와 비밀번호 확인이 같은지
                    if(!strPW.equals(strRePW)){
                        errRePW.setText("비밀번호를 재확인 해주세요");
                        errRePW.setVisibility(View.VISIBLE);
                    }
                    else {
                        errRePW.setVisibility(View.GONE);
                    }
                    if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$", strPW)){
                        errPW.setText("비밀번호 형식은 대소문자 구분, 숫자, 특수문자가 포함된 8~16글자 입니다.");
                        errPW.setVisibility(View.VISIBLE);
                    }
                    else{
                        errPW.setVisibility(View.GONE);
                    }


                    // 모두 통과하면
                    if(isAbleId && strPW.equals(strRePW)){
                        errId.setVisibility(View.GONE);
                        errRePW.setVisibility(View.GONE);
                        errPW.setVisibility(View.GONE);
                        errBirth.setVisibility(View.GONE);
                        //회원가입 하기

                        UserDTO user = getData();
                        Call<UserDTO> call = RetrofitHelper.getApiService().signup(user);
                        call.enqueue(new Callback<UserDTO>() {
                            @Override
                            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDTO> call, Throwable t) {
                                Log.e("실패", t.getMessage());
                            }
                        });


                    }
                }
                break;
            case R.id.btnCheck:
                strId = editId.getText().toString();
                
                // id가 비워져있는지
                if(strId.equals("")){
                    errId.setText("아이디를 비워둘 수 없습니다.");
                    errId.setVisibility(View.VISIBLE);
                }
                // id email 형식 체크
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(strId).matches()){
                    errId.setText("아이디는 Email 형식으로 작성해주세요");
                    errId.setVisibility(View.VISIBLE);
                }
                else{
                    errId.setVisibility(View.GONE);
                    // 중복확인
                    Log.e("호호","이메일 맞아");
                    editId.setEnabled(false);

                    if(true){
                        errId.setVisibility(View.GONE);
                        Toast.makeText(this, strId+"는 사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        isAbleId = true;
                    }
                    else{
                        errId.setText("중복되는 아이디 입니다.");
                        errId.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.tvBirth:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month +1;
                        d = dayOfMonth;

                        if(m<10 && d<10){
                            tvBirth.setText(y+"-0"+m+"-0"+d);
                        }
                        else if(m<10 && d>=10){
                            tvBirth.setText(y+"-0"+m+"-"+d);
                        }
                        else if(m>=10 && d<10){
                            tvBirth.setText(y+"-"+m+"-0"+d);
                        }
                        else{
                            tvBirth.setText(y+"-"+m+"-"+d);
                        }

                    }
                },y,m-1,d);

                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datePickerDialog.setMessage("생일을 선택해주세요");
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
                break;
        }
    }

    private UserDTO getData() {
        UserDTO data = new UserDTO(strId, strPW, strName, strBirth);
        return data;
    }

    void init(){
        editId = findViewById(R.id.editId);
        editPW = findViewById(R.id.editPW);
        editRePW =findViewById(R.id.editRePW);
        tvBirth = findViewById(R.id.tvBirth);
        errId = findViewById(R.id.errId);
        errPW = findViewById(R.id.errPW);
        errRePW =findViewById(R.id.errRePW);
        errBirth = findViewById(R.id.errBirth);
        btnSignup = findViewById(R.id.btnSignup);
        btnCheck = findViewById(R.id.btnCheck);
        editName = findViewById(R.id.editName);

        y=2000;
        m=1;
        d=1;
    }
}