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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editId, editPW, editRePW, editBirth;
    TextView errId, errPW, errRePW, errBirth;
    Button btnSignup, btnCheck;

    String strId, strPW, strRePW, strBirth;

    boolean isAbleId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

        btnSignup.setOnClickListener(this);
        btnCheck.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup:
                strId = editId.getText().toString();
                strPW = editPW.getText().toString();
                strRePW = editRePW.getText().toString();
                strBirth = editBirth.getText().toString();

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
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
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
                    editId.setClickable(false);

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
        }
    }

    void init(){
        editId = findViewById(R.id.editId);
        editPW = findViewById(R.id.editPW);
        editRePW =findViewById(R.id.editRePW);
        editBirth = findViewById(R.id.editBirth);
        errId = findViewById(R.id.errId);
        errPW = findViewById(R.id.errPW);
        errRePW =findViewById(R.id.errRePW);
        errBirth = findViewById(R.id.errBirth);
        btnSignup = findViewById(R.id.btnSignup);
        btnCheck = findViewById(R.id.btnCheck);
    }
}