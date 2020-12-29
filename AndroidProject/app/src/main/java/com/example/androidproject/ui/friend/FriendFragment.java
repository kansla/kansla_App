package com.example.androidproject.ui.friend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.R;

public class FriendFragment extends Fragment {
    View view;

    private ListView listView;
    static FriendAdapter adapter;

    int posi;

    private FriendViewModel friendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel =
                new ViewModelProvider(this).get(FriendViewModel.class);
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        adapter = new FriendAdapter();

        listView = view.findViewById(R.id.listview);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posi = position;
                TextView tvName = view.findViewById(R.id.name);
                String str = tvName.getText().toString();
                Log.d("test", "나다 "+str);
            }
        });

        dataSetting();

        return view;
    }

    private void dataSetting() {
        FriendAdapter adapter = new FriendAdapter();

        for(int i =0; i<20; i++){
            adapter.addItem("문예원"+i, "공쥬");
        }
        listView.setAdapter(adapter);
    }
}