package com.example.androidproject.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.InfomationActivity;
import com.example.androidproject.MainActivity;
import com.example.androidproject.ManualActivity;
import com.example.androidproject.R;


public class SettingFragment extends Fragment implements View.OnClickListener{

    View view;

    ImageView btnInfo, btnManual;

    private SettingViewModel settingViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        btnInfo = view.findViewById(R.id.info);
        btnManual = view.findViewById(R.id.manual);

        btnInfo.setOnClickListener(this);
        btnManual.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.info:
                intent = new Intent(getActivity(), InfomationActivity.class);
                break;
            case R.id.manual:
                intent = new Intent(getActivity(), ManualActivity.class);
                break;
            default:
        }
        startActivity(intent);
    }
}
