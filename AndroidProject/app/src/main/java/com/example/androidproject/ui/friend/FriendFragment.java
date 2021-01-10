package com.example.androidproject.ui.friend;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.API.RetrofitHelper;
import com.example.androidproject.AddFriendActivity;
import com.example.androidproject.CustomDialogFragment;
import com.example.androidproject.DTO.FriendsDTO;
import com.example.androidproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    View view;

    private ListView listView;
    static FriendAdapter adapter;

    int posi;

    private FriendViewModel friendViewModel;

    String email;
    TextView tvName, tvContents;
    SharedPreferences auto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel =
                new ViewModelProvider(this).get(FriendViewModel.class);
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        adapter = new FriendAdapter();

        auto = this.getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);

        tvName = view.findViewById(R.id.name);
        tvName.setText(auto.getString("inputName","null"));
        tvContents = view.findViewById(R.id.contents);
        tvContents.setText(auto.getString("inputContents","null"));
        email = auto.getString("inputId", "null");

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

        FriendsDTO user = new FriendsDTO(email);
        Call<FriendsDTO> call = RetrofitHelper.getApiService().friend_list(user);
        call.enqueue(new Callback<FriendsDTO>() {
            @Override
            public void onResponse(Call<FriendsDTO> call, Response<FriendsDTO> response) {
                FriendsDTO result = response.body();

                adapter.clearItem();
                try {
                    for (int i=0; i<result.getCount(); i++){
                        adapter.addItem(result.getFriends().get(i).getName(),
                                result.getFriends().get(i).getStatus_msg(),
                                result.getFriends().get(i).getEmail());
                    }
                }catch (Exception e){
                    Log.e("친구", "없음");
                }

                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<FriendsDTO> call, Throwable t) {
                Log.e("errFriend", "통신 안됨: "+t.getMessage());
            }
        });
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDialogFragment dialog = CustomDialogFragment.newInstance("채팅을 시작하시겠습니까?",2);
                dialog.show(getFragmentManager(), "dialog");
                SharedPreferences.Editor editor = auto.edit();
                editor.putString("second_email", adapter.getItem(position).getEmali());
                editor.apply();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addFri:
                Toast.makeText(getContext(), "addFriend", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}