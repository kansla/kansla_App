package com.example.androidproject.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.InfomationActivity;
import com.example.androidproject.ManualActivity;
import com.example.androidproject.CustomDialogFragment;
import com.example.androidproject.R;


public class SettingFragment extends Fragment implements View.OnClickListener{

    View view;

    ImageView btnInfo, btnManual;
    TextView tvName, tvContents;
    Button btnLogout;

    private SettingViewModel settingViewModel;

    SharedPreferences auto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        init();

        auto = this.getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        tvName.setText(auto.getString("inputName","null"));
        tvContents.setText(auto.getString("inputContents","null"));

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.info:
                intent = new Intent(getActivity(), InfomationActivity.class);
                startActivity(intent);
                break;
            case R.id.manual:
                intent = new Intent(getActivity(), ManualActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                dialogShow();
                break;
        }
    }

    private void dialogShow() {
        CustomDialogFragment dialog = CustomDialogFragment.newInstance("채팅을 시작할까요?",1);
        dialog.show(getFragmentManager(), "dialog");
    }

    private void init() {
        btnInfo = view.findViewById(R.id.info);
        btnManual = view.findViewById(R.id.manual);
        tvName = view.findViewById(R.id.tvName);
        tvContents = view.findViewById(R.id.tvContents);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnInfo.setOnClickListener(this);
        btnManual.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }
}
