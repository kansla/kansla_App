package com.example.androidproject.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.R
import com.example.androidproject.databinding.ActivityChatBinding
import com.example.androidproject.ui.chatRoom.ChatList
import com.example.androidproject.ui.chatRoom.ChatListAdapter


class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityChatBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_chat)

        var mAdapter = ChatContentAdapter()
        var chatContent = ObservableArrayList<ChatContent>()
        binding.recyclerview.adapter = mAdapter
        binding.chatContent = chatContent

        binding.recyclerview.setHasFixedSize(true)
        chatContent.add(ChatContent("문예원","떡볶이 사줘",R.drawable.surprise.toString(),
                "오후 8:21", "ansdPdnjs", R.drawable.surprise.toString()))
        chatContent.add(ChatContent("누군가","원래 이런건 말이지 사다리타기로 해서정하는거야",R.drawable.surprise.toString(),
                "오후 8:22", "rlathdus", R.drawable.surprise.toString()))
    }

    override fun onBackPressed() {
        finish()
    }
}

@BindingAdapter("item")
fun bindItem(recyclerView: RecyclerView, chat: ObservableArrayList<ChatContent>) {
    val adapter: ChatContentAdapter? = recyclerView.adapter as ChatContentAdapter?
    adapter?.setItem(chat)
}