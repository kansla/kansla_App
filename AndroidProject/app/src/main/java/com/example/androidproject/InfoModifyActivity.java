package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class InfoModifyActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editName, editContents, editEmail, editOrgPW, editNewPW;
    TextView tvBirth;
    Button btnSave, btnCheck;

    String strName, strContents, strEmail, strOrgPW, strNewPW, strOrgEmail;
    boolean isAbleEmail = false;

    int y=0, m=0, d=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_modify);
        setTitle("정보 수정");

        init();

        btnSave.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        tvBirth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                strName = editName.getText().toString();
                strContents = editContents.getText().toString();
                strEmail = editEmail.getText().toString();
                strOrgPW = editOrgPW.getText().toString();
                strNewPW = editNewPW.getText().toString();

                if(strName.equals("") || strContents.equals("") || strEmail.equals("") || strOrgPW.equals("") || strNewPW.equals("")){
                    Toast.makeText(this, "비어있는 칸이 없도록 채워주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(!isAbleEmail){
                    Toast.makeText(this, "중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!strOrgPW.equals("asdf123456!!!!")){
                    Toast.makeText(this, "기존 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$", strNewPW)){
                    Toast.makeText(this, "비밀번호 형식은 대소문자 구분, 숫자, 특수문자가 포함된 8~16글자 입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // 바꾸자
                    finish();
                }
                break;
            case R.id.birth:
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
            case R.id.btnCheck2:
                strEmail = editEmail.getText().toString();
                if(strEmail.equals("")){
                    Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(strEmail.equals(strOrgEmail)){
                    Toast.makeText(this, "기존 이메일을 사용합니다.", Toast.LENGTH_SHORT).show();
                    editEmail.setEnabled(false);
                    isAbleEmail = true;
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
                    Toast.makeText(this, "이메일 형식으로 작성해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(true){
                        editEmail.setEnabled(false);
                        isAbleEmail = true;
                        Toast.makeText(this, "중복 확인 완료", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void init() {
        editName = findViewById(R.id.name);
        editContents = findViewById(R.id.contents);
        editEmail = findViewById(R.id.email);
        editOrgPW = findViewById(R.id.orgPW);
        editNewPW = findViewById(R.id.newPW);
        tvBirth = findViewById(R.id.birth);
        btnSave = findViewById(R.id.btnSave);
        btnCheck = findViewById(R.id.btnCheck2);

        strOrgEmail = editEmail.getText().toString();

        y= Integer.parseInt(tvBirth.getText().toString().split("-")[0]);
        m= Integer.parseInt(tvBirth.getText().toString().split("-")[1]);
        d= Integer.parseInt(tvBirth.getText().toString().split("-")[2]);
    }
}