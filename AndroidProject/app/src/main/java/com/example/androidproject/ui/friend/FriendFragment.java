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

import com.example.androidproject.API.Posts;
import com.example.androidproject.API.RetrofitAPI;
import com.example.androidproject.R;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendFragment extends Fragment {
    View view;

    private ListView listView;
    static FriendAdapter adapter;

    int posi;

    private FriendViewModel friendViewModel;

    private RetrofitAPI retrofitAPI;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);
        service.getPosts().enqueue(new Callback<Posts[]>() {
            @Override
            public void onResponse(Call<Posts[]> call, Response<Posts[]> response) {
                if(response.isSuccessful()){
                    List<Posts> data = Arrays.asList(response.body());
                    Log.d("TAG_AN", "성공: "+data.get(0).getTitle());
                    FriendAdapter adapter = new FriendAdapter();
                    for(int i=1;i<=10;i++){
                        adapter.addItem("title: "+data.get(i).getTitle(), "Contents: "+data.get(i).getBody());
                    }
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Posts[]> call, Throwable t) {
                Log.d("TAG_AN", "실패: "+t.getMessage().toString());
            }
        });


        //dataSetting();

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