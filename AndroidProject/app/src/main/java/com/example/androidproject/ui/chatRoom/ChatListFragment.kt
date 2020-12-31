package com.example.androidproject.ui.chatRoom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.R
import com.example.androidproject.databinding.FragmentChatListBinding
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment() {
    lateinit var bind: FragmentChatListBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_chat_list,
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}

@BindingAdapter("item")
fun bindItem(recyclerView: RecyclerView, chat: ObservableArrayList<ChatList>) {
    val adapter: ChatListAdapter? = recyclerView.adapter as ChatListAdapter?
    adapter?.setItem(chat)
}