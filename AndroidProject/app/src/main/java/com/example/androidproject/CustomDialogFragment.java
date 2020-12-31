package com.example.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "CustomDialogFragment";    private static final String ARG_DIALOG_MAIN_MSG = "dialog_main_msg";
    private String mMainMsg;
    private MainActivity MainActivity;

    public static CustomDialogFragment newInstance(String mainMsg) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DIALOG_MAIN_MSG, mainMsg);
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);
        ((TextView)view.findViewById(R.id.textView)).setText(mMainMsg);
        view.findViewById(R.id.btnYes).setOnClickListener(this);
        view.findViewById(R.id.btnNo).setOnClickListener(this);
        builder.setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void dismissDialog() {
        this.dismiss();
    }

    @Override    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnYes:
                SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
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
        }
    }
}
