package com.example.androidproject.ui.chatRoom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.R
import com.example.androidproject.databinding.FragmentChatRoomBinding
import com.example.androidproject.ui.chatting.ChattingActivity

class ChatRoomFragment : Fragment() {

    private lateinit var dashboardViewModel: ChatRoomViewModel

    internal lateinit var preferences: SharedPreferences

    lateinit var bind: FragmentChatRoomBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_chat_room,
                container,
                false
        )
        var mAdapter = ChatListAdapter()
        var chatList = ObservableArrayList<ChatList>()
        bind.recyclerview.adapter = mAdapter
        bind.chatList = chatList
        chatList.add(ChatList("김소연", "그래서 난 눈누난나 빰빠바바바바바바바바바밥ㅁ 오예오예ㅔ 나외이ㅏ", (R.drawable.profile).toString()))
        chatList.add(ChatList("문예원", "배고파요", (R.drawable.profile).toString()))

        bind.fab.setOnClickListener {
            Toast.makeText(activity, "새로운 채팅방 만들기", Toast.LENGTH_LONG).show()
            Log.d("abo", "새 채팅방 만들기")
            /*activity?.let {
                val intent = Intent(context, WriteDiary::class.java)
                startActivity(intent)
                activity?.finish()
            }*/
        }

        return bind.root
    }
}

@BindingAdapter("item")
fun bindItem(recyclerView: RecyclerView, chat: ObservableArrayList<ChatList>) {
    val adapter: ChatListAdapter? = recyclerView.adapter as ChatListAdapter?
    adapter?.setItem(chat)
}