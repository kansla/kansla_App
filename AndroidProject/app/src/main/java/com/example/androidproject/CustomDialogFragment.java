package com.example.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.androidproject.API.RetrofitHelper;
import com.example.androidproject.DTO.AddFriend;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "CustomDialogFragment";    private static final String ARG_DIALOG_MAIN_MSG = "dialog_main_msg";
    private String mMainMsg;
    static int type = 0;

    SharedPreferences auto;

    public static CustomDialogFragment newInstance(String mainMsg, int num) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DIALOG_MAIN_MSG, mainMsg);
        type = num;
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMainMsg = getArguments().getString(ARG_DIALOG_MAIN_MSG);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(type == 1) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);
            ((TextView) view.findViewById(R.id.textView)).setText(mMainMsg);
            view.findViewById(R.id.btnYes).setOnClickListener(this);
            view.findViewById(R.id.btnNo).setOnClickListener(this);
            builder.setCancelable(false);
            builder.setView(view);
        }
        else if(type == 2){
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog2, null);
            ((TextView) view.findViewById(R.id.textView)).setText(mMainMsg);
            view.findViewById(R.id.btnYes2).setOnClickListener(this);
            view.findViewById(R.id.btnNo).setOnClickListener(this);
            builder.setCancelable(false);
            builder.setView(view);
        }
        return builder.create();
    }

    private void dismissDialog() {
        this.dismiss();
    }

    @Override    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnYes:
                auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();

                editor.clear();
                editor.commit();

                MainActivity mainActivity = (MainActivity)MainActivity.mainActivity;
                mainActivity.finish();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                dismissDialog();
                break;
            case R.id.btnNo:
                dismissDialog();
                break;
            case R.id.btnYes2:
                MainActivity mainActivity2 = (MainActivity)MainActivity.mainActivity;

                auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
                Log.e("shareEmail", auto.getString("second_email","email"));
                AddFriend user = new AddFriend(auto.getString("inputId", ""), auto.getString("second_email",""));
                Call<AddFriend> call = RetrofitHelper.getApiService().chat_start(user);
                call.enqueue(new Callback<AddFriend>() {
                    @Override
                    public void onResponse(Call<AddFriend> call, Response<AddFriend> response) {
                        if (response.isSuccessful()) {
                            int result = response.code();
                            if (result == 200) {
                                Toast.makeText(mainActivity2, "채팅방이 생성되었습니다!", Toast.LENGTH_SHORT).show();
                            } else if (result == 204) {
                                Toast.makeText(mainActivity2, "이미 존재하는 채팅방 입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddFriend> call, Throwable t) {
                        Log.e("err", "통신 안됨");
                    }
                });
                dismissDialog();
                break;
        }
    }
}
