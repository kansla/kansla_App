package com.example.androidproject.ui.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidproject.R;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {

    private TextView tvName, tvContents;
    private ImageView img;
    static  String strName, strContents;
    FriendFragment friendFragment = new FriendFragment();

    static private ArrayList<FriendItem> friendItemArrayList = new ArrayList<FriendItem>();

    public FriendAdapter(){

    }

    @Override
    public int getCount() {
        return friendItemArrayList.size();
    }

    @Override
    public FriendItem getItem(int position) {
        return friendItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_friend, parent, false);
        }

        tvName = (TextView) convertView.findViewById(R.id.name);
        tvContents = (TextView) convertView.findViewById(R.id.contents);

        final FriendItem friendItem = friendItemArrayList.get(position);

        tvName.setText(friendItem.getName());
        tvContents.setText(friendItem.getContents());

        return convertView;
    }

    public void addItem(String strName, String strContents, String strEmail){
        FriendItem item = new FriendItem();
        item.setName(strName);
        item.setContents(strContents);
        item.setEmali(strEmail);

        friendItemArrayList.add(item);
    }

    public void clearItem(){
        friendItemArrayList.clear();
    }

}
