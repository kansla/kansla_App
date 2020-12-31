package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
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

import com.example.androidproject.API.RetrofitHelper;
import com.example.androidproject.DTO.ResponseLogin;
import com.example.androidproject.DTO.UserDTO;
import com.example.androidproject.DTO.UserModifyDTO;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoModifyActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editName, editContents, editEmail, editOrgPW, editNewPW;
    TextView tvBirth;
    Button btnSave, btnCheck;

    String strName, strContents, strEmail, strOrgPW, strNewPW, strOrgEmail;
    boolean isAbleEmail = false;

    int y=0, m=0, d=0;

    SharedPreferences auto;
    SharedPreferences.Editor editor;

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

                auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

                if(strName.equals("") || strContents.equals("") || strEmail.equals("") || strOrgPW.equals("") || strNewPW.equals("")){
                    Toast.makeText(this, "비어있는 칸이 없도록 채워주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(!isAbleEmail){
                    Toast.makeText(this, "중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!strOrgPW.equals(auto.getString("inputPW","null"))){
                    Toast.makeText(this, "기존 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$", strNewPW)){
                    Toast.makeText(this, "비밀번호 형식은 대소문자 구분, 숫자, 특수문자가 포함된 8~16글자 입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // 바꾸자
                    UserModifyDTO user = getData();
                    Call<UserModifyDTO> call = RetrofitHelper.getApiService().modify(user);
                    call.enqueue(new Callback<UserModifyDTO>() {
                        @Override
                        public void onResponse(Call<UserModifyDTO> call, Response<UserModifyDTO> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(InfoModifyActivity.this, "정보 수정 완료", Toast.LENGTH_SHORT).show();

                                auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                editor = auto.edit();
                                editor.putString("inputName", strName);
                                editor.putString("inputContents", strContents);
                                editor.putString("inputId", strEmail);
                                editor.putString("inputPW", strNewPW);
                                editor.putString("inputBirth", tvBirth.getText().toString());
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), InfomationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModifyDTO> call, Throwable t) {
                            Log.e("err","통신 안됨"+t.getMessage());
                        }
                    });
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
                    UserDTO user = new UserDTO(strEmail);
                    Call<UserDTO> call = RetrofitHelper.getApiService().check_email(user);
                    call.enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            if(response.isSuccessful()){
                                int result = response.code();
                                if(result == 200){
                                    Toast.makeText(InfoModifyActivity.this, strEmail+"는 사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                    editEmail.setEnabled(false);
                                    isAbleEmail = true;
                                }
                                else if(result == 204){
                                    Toast.makeText(InfoModifyActivity.this, "중복되는 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable t) {
                            Log.e("err", "통신 안됨: "+t.getMessage());
                        }
                    });
                }
                break;
        }
    }

    private UserModifyDTO getData() {
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        UserModifyDTO data = new UserModifyDTO(auto.getString("inputID","null"),
                strEmail, strName, strNewPW, tvBirth.getText().toString(), "", strContents);

        return data;
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

        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        strName = auto.getString("inputName","null");
        strContents = auto.getString("inputContents","null");
        strOrgEmail = auto.getString("inputId","null");
        tvBirth.setText(auto.getString("inputBirth","null").substring(0,10));

        editName.setText(strName);
        editContents.setText(strContents);
        editEmail.setText(strOrgEmail);

        y= Integer.parseInt(tvBirth.getText().toString().split("-")[0]);
        m= Integer.parseInt(tvBirth.getText().toString().split("-")[1]);
        d= Integer.parseInt(tvBirth.getText().toString().split("-")[2]);

    }
}